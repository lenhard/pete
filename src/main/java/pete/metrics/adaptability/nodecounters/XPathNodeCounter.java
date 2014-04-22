package pete.metrics.adaptability.nodecounters;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactoryConfigurationException;

import net.sf.saxon.lib.NamespaceConstant;
import net.sf.saxon.xpath.XPathEvaluator;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import pete.executables.AnalysisException;
import pete.metrics.adaptability.elements.AdaptableElement;
import pete.metrics.adaptability.elements.AdaptableElements;
import bpp.domain.BpelNamespaceContext;

public class XPathNodeCounter implements NodeCounter {

	private ConcurrentHashMap<String, AtomicInteger> elementNumbers;

	private List<AdaptableElement> elements;

	private XPathEvaluator xpath;

	public XPathNodeCounter() {
		elementNumbers = new ConcurrentHashMap<>();
		elements = new AdaptableElements().getElements();
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
		elements.forEach(element -> checkForElement(element, document));
	}

	private void checkForElement(AdaptableElement element, Document document) {
		try {
			XPathExpression expr = xpath
					.compile(element.getLocatorExpression());
			NodeList matchedLines = (NodeList) expr.evaluate(document,
					XPathConstants.NODESET);
			addOccurences(element.getName(), matchedLines.getLength());
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
			List<String> sortedKeyList = new LinkedList<>();
			sortedKeyList.addAll(elementNumbers.keySet());
			sortedKeyList.sort((e1, e2) -> e1.compareTo(e2));
			for (String key : sortedKeyList) {
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
