package pete.metrics.adaptability;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import pete.executables.AnalysisException;

public class BpmnInspector {

	private static final String BPMN_NAMESPACE = "http://www.omg.org/spec/BPMN/20100524/MODEL";

	String isCorrectNamespace(Document dom) {
		Element root = null;
		try {
			root = dom.getDocumentElement();
		} catch (NullPointerException e) {
			return "false";
		}
		if (BPMN_NAMESPACE.equals(root.getNamespaceURI())) {
			return "true";
		} else {
			return "false";
		}
	}

	String isExecutable(Node process) {
		NamedNodeMap attributes = process.getAttributes();

		for (int j = 0; j < attributes.getLength(); j++) {
			Node attribute = attributes.item(j);
			if ("isExecutable".equals(attribute.getLocalName())
					&& "true".equals(attribute.getNodeValue())) {
				return "true";
			}
		}

		return "false";
	}

	int getNumberOfChildren(Node node) {
		NodeList children = node.getChildNodes();
		int result = 0;

		if (children == null) {
			// do noting, result is zero
		} else {
			result += children.getLength();
			for (int i = 0; i < children.getLength(); i++) {
				result += getNumberOfChildren(children.item(i));
			}
		}
		return result;
	}

	public Node getProcess(Document dom) throws AnalysisException {
		NodeList nodes = getChildrenOfDefinitions(dom);
		if (nodes == null) {
			throw new AnalysisException(
					"File cannot be analyzed: no definitions element or definitions element is empty");
		}
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if ("process".equals(node.getLocalName())) {
				return node;
			}
		}
		throw new AnalysisException(
				"File cannot be analyzed: no process element found");
	}

	private NodeList getChildrenOfDefinitions(Document dom) {
		NodeList nodes = null;
		try {
			nodes = dom.getChildNodes();
		} catch (NullPointerException e) {
			return null;
		}
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if ("definitions".equals(node.getLocalName())) {
				return node.getChildNodes();
			}
		}
		return null;
	}

}