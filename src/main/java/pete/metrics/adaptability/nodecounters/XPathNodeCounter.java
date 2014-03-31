package pete.metrics.adaptability.nodecounters;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactoryConfigurationException;

import net.sf.saxon.lib.NamespaceConstant;
import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.xpath.XPathEvaluator;

import org.w3c.dom.Document;

import pete.executables.AnalysisException;
import pete.metrics.adaptability.AdaptableElement;
import pete.metrics.adaptability.RelevantElements;
import bpp.domain.BpelNamespaceContext;

public class XPathNodeCounter implements NodeCounter {

	private HashMap<String, AtomicInteger> elementNumbers;

	private List<AdaptableElement> elements;

	private XPathEvaluator xpath;

	public XPathNodeCounter() {
		elementNumbers = new HashMap<>();
		elements = new RelevantElements().getElements();
		try {
			createXPathEvaluator();
		} catch (XPathFactoryConfigurationException e) {
			throw new AnalysisException(e);
		}
	}

	private void createXPathEvaluator()
			throws XPathFactoryConfigurationException {
		XPathFactory xpathFactory = XPathFactory
				.newInstance(NamespaceConstant.OBJECT_MODEL_SAXON);
		xpath = (XPathEvaluator) xpathFactory.newXPath();
		xpath.getConfiguration().setLineNumbering(true);
		xpath.setNamespaceContext(new BpelNamespaceContext());
	}

	@Override
	public void addToCounts(Document document) {
		for (AdaptableElement element : elements) {
			checkForElement(element, document);
		}
	}

	private void checkForElement(AdaptableElement element, Document document) {
		try {
			XPathExpression expr = xpath
					.compile(element.getLocatorExpression());
			@SuppressWarnings("unchecked")
			List<NodeInfo> matchedLines = (List<NodeInfo>) expr.evaluate(
					document, XPathConstants.NODESET);
			addOccurences(element.getName(), matchedLines.size());
		} catch (XPathExpressionException e) {
			throw new AnalysisException("Element " + element.getName() + ": ",
					e);
		}
	}

	private void addOccurences(String elementName, int occurences) {
		AtomicInteger currentNumber = elementNumbers.get(elementName);
		if (currentNumber == null) {
			elementNumbers.put(elementName, new AtomicInteger(occurences));
		} else {
			currentNumber.addAndGet(occurences);
		}
	}

	@Override
	public void writeToCsv(Path file) {
		try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(file,
				Charset.defaultCharset()))) {
			writer.println("element;number");
			for (String key : elementNumbers.keySet()) {
				AtomicInteger value = elementNumbers.get(key);
				writer.println(key + ";" + value);
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	@Override
	public Map<String, AtomicInteger> getElementNumbers() {
		return Collections.unmodifiableMap(elementNumbers);
	}

}
