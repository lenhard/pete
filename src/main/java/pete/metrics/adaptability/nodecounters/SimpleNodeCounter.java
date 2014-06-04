package pete.metrics.adaptability.nodecounters;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import pete.metrics.adaptability.BpmnInspector;
import pete.metrics.adaptability.elements.AdaptableElements;

/**
 * Counts occurences of XML elements in a BPMN process, sorted by the name of
 * the element. The list of relevant elements is determined internally
 *
 */
public class SimpleNodeCounter implements NodeCounter {

	private HashMap<String, AtomicInteger> elements;

	private boolean isStrict;

	private List<String> relevantElements;

	public SimpleNodeCounter() {
		setUp(false);
	}

	public SimpleNodeCounter(boolean isStrict) {
		setUp(isStrict);
	}

	private void setUp(boolean isStrict) {
		elements = new HashMap<String, AtomicInteger>();
		this.isStrict = isStrict;
		relevantElements = new AdaptableElements().getElementNames();
	}

	@Override
	public Map<String, AtomicInteger> addToCounts(Document dom) {
		Node process = new BpmnInspector().getProcess(dom);
		addNodeToCounts(process);
		return getAbsoluteElementNumbers();
	}

	private void addNodeToCounts(Node node) {
		// ignore text nodes
		if (isTextNode(node)) {
			return;
		}

		addToMap(node);

		NodeList children = node.getChildNodes();
		if (children != null) {
			for (int i = 0; i < children.getLength(); i++) {
				addNodeToCounts(children.item(i));
			}
		}
	}

	private boolean isTextNode(Node node) {
		return node.getNodeType() == Node.TEXT_NODE;
	}

	private void addToMap(Node node) {
		String nodeName = node.getLocalName();
		if (nodeName == null) {
			return;
		}

		if (isStrict && !relevantElements.contains(nodeName)) {
			return;
		}

		if (elements.containsKey(nodeName)) {
			AtomicInteger count = elements.get(nodeName);
			count.incrementAndGet();
		} else {
			elements.put(nodeName, new AtomicInteger(1));
		}
	}

	@Override
	public void writeToCsv(Path file) {
		try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(file,
				Charset.defaultCharset()))) {
			writer.println("element;number");
			List<String> sortedKeyList = new LinkedList<>();
			sortedKeyList.addAll(elements.keySet());
			sortedKeyList.sort((e1, e2) -> e1.compareTo(e2));
			for (String key : sortedKeyList) {
				AtomicInteger value = elements.get(key);
				writer.println(key + ";" + value);
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	@Override
	public Map<String, AtomicInteger> getAbsoluteElementNumbers() {
		return Collections.unmodifiableMap(elements);
	}

	@Override
	public Map<String, AtomicInteger> getProcessOccurences() {
		throw new IllegalStateException("not implemented");
	}
}
