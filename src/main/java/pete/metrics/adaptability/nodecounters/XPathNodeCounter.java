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

	private ConcurrentHashMap<String, AtomicInteger> absoluteElementNumbers;

	private ConcurrentHashMap<String, AtomicInteger> documentElementNumbers;

	private ConcurrentHashMap<String, AtomicInteger> processOccurrences;

	private List<AdaptableElement> elements;

	private XPathEvaluator xpath;

	public XPathNodeCounter() {
		absoluteElementNumbers = new ConcurrentHashMap<>();
		processOccurrences = new ConcurrentHashMap<>();
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
	public Map<String, AtomicInteger> addToCounts(Document document) {
		documentElementNumbers = new ConcurrentHashMap<>();
		elements.forEach(element -> checkForElement(element, document));
		return documentElementNumbers;
	}

	private void checkForElement(AdaptableElement element, Document document) {
		String elementName = element.getName();
		try {
			XPathExpression expr = xpath
					.compile(element.getLocatorExpression());
			int occurences = ((NodeList) expr.evaluate(document,
					XPathConstants.NODESET)).getLength();
			addOccurences(elementName, occurences, absoluteElementNumbers);
			addOccurences(elementName, occurences, documentElementNumbers);
			addProcessOccurences(elementName, occurences);
		} catch (XPathExpressionException e) {
			throw new AnalysisException("Element " + elementName + ": ", e);
		}
	}

	private void addProcessOccurences(String elementName, int occurences) {
		AtomicInteger current = getElementInMap(elementName, processOccurrences);
		if (occurences > 0) {
			current.incrementAndGet();
		}
	}

	private void addOccurences(String elementName, int occurences,
			ConcurrentHashMap<String, AtomicInteger> mapToAdd) {
		AtomicInteger currentNumber = getElementInMap(elementName, mapToAdd);
		currentNumber.addAndGet(occurences);
	}

	private AtomicInteger getElementInMap(String elementName,
			ConcurrentHashMap<String, AtomicInteger> mapToAdd) {
		AtomicInteger currentNumber = mapToAdd.get(elementName);
		if (currentNumber == null) {
			currentNumber = new AtomicInteger(0);
			mapToAdd.put(elementName, currentNumber);
		}
		return currentNumber;
	}

	@Override
	public void writeToCsv(Path file) {
		// currently write the occurences of elements per process
		writeRawData(file, processOccurrences, ";");
	}

	private void writeRawData(Path file,
			Map<String, AtomicInteger> elementNumbers, String delimiter) {
		try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(file,
				Charset.defaultCharset()))) {
			writer.println("element" + delimiter + "number");
			List<String> sortedKeyList = new LinkedList<>();
			sortedKeyList.addAll(elementNumbers.keySet());
			sortedKeyList.sort((e1, e2) -> e1.compareTo(e2));
			for (String key : sortedKeyList) {
				AtomicInteger value = elementNumbers.get(key);
				writer.println(key + delimiter + value);
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	@Override
	public Map<String, AtomicInteger> getAbsoluteElementNumbers() {
		return Collections.unmodifiableMap(absoluteElementNumbers);
	}

	@Override
	public Map<String, AtomicInteger> getProcessOccurences() {
		return Collections.unmodifiableMap(processOccurrences);
	}

}
