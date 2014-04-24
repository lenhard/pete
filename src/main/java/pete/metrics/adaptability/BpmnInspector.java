package pete.metrics.adaptability;

import java.nio.file.InvalidPathException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import pete.executables.AnalysisException;
import de.uniba.wiai.lspi.ws1213.ba.application.BPMNReferenceValidator;
import de.uniba.wiai.lspi.ws1213.ba.application.BPMNReferenceValidatorImpl;
import de.uniba.wiai.lspi.ws1213.ba.application.ValidationResult;
import de.uniba.wiai.lspi.ws1213.ba.application.ValidatorException;

public final class BpmnInspector {

	private static final String BPMN_NAMESPACE = "http://www.omg.org/spec/BPMN/20100524/MODEL";

	private BPMNReferenceValidator referenceValidator;

	public BpmnInspector() {
		try {
			referenceValidator = new BPMNReferenceValidatorImpl();
		} catch (ValidatorException e) {
			throw new AnalysisException(e);
		}
	}

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

	String hasReferenceIssues(String path) {
		ValidationResult result;
		try {
			result = referenceValidator.validateSingleFile(path);
		} catch (ValidatorException | InvalidPathException
				| NullPointerException e) {
			System.err.println(e.getMessage());
			return "false";
		}

		if (!result.isValid()) {
			return "true";
		} else {
			return "false";
		}
	}

	int getNumberOfChildren(Node node) {
		if (node.getLocalName() == null) {
			return 0;
		}

		NodeList children = node.getChildNodes();
		int result = 1;

		if (children == null) {
			// do noting, result is zero
		} else {
			for (int i = 0; i < children.getLength(); i++) {
				result += getNumberOfChildren(children.item(i));
			}
		}
		return result;
	}

	String getImportTypes(Document document) {
		NodeList nodes = getChildrenOfDefinitions(document);
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if ("import".equals(node.getLocalName())) {
				NamedNodeMap attributes = node.getAttributes();
				Node importType = attributes.getNamedItem("importType");
				if (importType != null) {
					result.append(importType.getNodeValue() + " ");
				}
			}
		}
		return result.toString();
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