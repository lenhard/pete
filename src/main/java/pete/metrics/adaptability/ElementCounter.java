package pete.metrics.adaptability;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ElementCounter {

	private HashMap<String, AtomicInteger> elements;

	private boolean isStrict;

	private List<String> relevantConstructs;

	public ElementCounter(boolean isStrict) {
		elements = new HashMap<String, AtomicInteger>();
		this.isStrict = isStrict;
		relevantConstructs = new RelevantConstructs().getRelevantConstructs();
	}

	public void addToCounts(Node node) {
		addToMap(node);

		NodeList children = node.getChildNodes();
		if (children != null) {
			for (int i = 0; i < children.getLength(); i++) {
				addToCounts(children.item(i));
			}
		}
	}

	private void addToMap(Node node) {
		String nodeName = node.getLocalName();
		if (isStrict && !relevantConstructs.contains(nodeName)) {
			return;
		}

		if (elements.containsKey(nodeName)) {
			AtomicInteger count = elements.get(nodeName);
			count.incrementAndGet();
		} else {
			elements.put(nodeName, new AtomicInteger(1));
		}
	}

	public void clear() {
		elements = new HashMap<String, AtomicInteger>();
	}

	public void writeToCsv(Path file) {
		try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(file,
				Charset.defaultCharset()))) {
			writer.println("element;number");
			for (String key : elements.keySet()) {
				AtomicInteger value = elements.get(key);
				writer.println(key + ";" + value);
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
}
