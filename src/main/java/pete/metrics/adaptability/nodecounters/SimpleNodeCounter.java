package pete.metrics.adaptability.nodecounters;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
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
		setUp(true);
	}

	public SimpleNodeCounter(boolean isStrict) {
		setUp(isStrict);
	}

	private void setUp(boolean isStrict) {
		elements = new HashMap<String, AtomicInteger>();
		this.isStrict = isStrict;
		relevantElements = new ArrayList<>();
		relevantElements.add("activity");
		relevantElements.add("adHocSubProcess");
		relevantElements.add("boundaryEvent");
		relevantElements.add("businessRuleTask");
		relevantElements.add("callActivity");
		relevantElements.add("cancelEventDefinition");
		relevantElements.add("catchEvent");
		relevantElements.add("compensateEventDefinition");
		relevantElements.add("complexGateway");
		relevantElements.add("conditionalEventDefinition");
		relevantElements.add("dataAssociation");
		relevantElements.add("dataInput");
		relevantElements.add("dataInputAssociation");
		relevantElements.add("dataObject");
		relevantElements.add("dataOutput");
		relevantElements.add("dataOutputAssociation");
		relevantElements.add("dataStore");
		relevantElements.add("endEvent");
		relevantElements.add("error");
		relevantElements.add("errorBoundaryEvent");
		relevantElements.add("errorEventDefinition");
		relevantElements.add("escalation");
		relevantElements.add("escalationEventDefinition");
		relevantElements.add("event");
		relevantElements.add("eventBasedGateway");
		relevantElements.add("extension");
		relevantElements.add("extensionElements");
		relevantElements.add("formalExpression");
		relevantElements.add("gateway");
		relevantElements.add("globalBusinessRuleTask");
		relevantElements.add("globalManualTask");
		relevantElements.add("globalScriptTask");
		relevantElements.add("globalUserTask");
		relevantElements.add("globalTask");
		relevantElements.add("implicitThrowEvent");
		relevantElements.add("inclusiveGateway");
		relevantElements.add("inputSet");
		relevantElements.add("intermediateCatchEvent");
		relevantElements.add("intermediateThrowEvent");
		relevantElements.add("lane");
		relevantElements.add("manualTask");
		relevantElements.add("message");
		relevantElements.add("messageEventDefinition");
		relevantElements.add("messageFlow");
		relevantElements.add("messageFlowAssociation");
		relevantElements.add("loopCharacteristics");
		relevantElements.add("multiInstanceLoopCharacteristics");
		relevantElements.add("process");
		relevantElements.add("receiveTask");
		relevantElements.add("scripTask");
		relevantElements.add("script");
		relevantElements.add("sendTask");
		relevantElements.add("sequenceFlow");
		relevantElements.add("serviceTask");
		relevantElements.add("signal");
		relevantElements.add("signalEventDefinition");
		relevantElements.add("standardLoopCharacteristics");
		relevantElements.add("endEvent");
		relevantElements.add("subProcess");
		relevantElements.add("task");
		relevantElements.add("terminateEventDefinition");
		relevantElements.add("throwEvent");
		relevantElements.add("timerEventDefinition");
		relevantElements.add("transaction");
		relevantElements.add("userTask");
		relevantElements.add("isForCompensation");
	}

	@Override
	public void addToCounts(Document dom) {
		Node process = new BpmnInspector().getProcess(dom);
		addNodeToCounts(process);
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
	public Map<String, AtomicInteger> getElementNumbers() {
		return Collections.unmodifiableMap(elements);
	}
}
