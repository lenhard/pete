package pete.metrics.adaptability;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import pete.executables.FileAnalyzer;
import pete.reporting.ReportEntry;

public class AdaptabilityAnalyzer implements FileAnalyzer {

	@Override
	public List<ReportEntry> analyzeFile(Path filePath) {
		System.out.println("Analyzing " + filePath + " for adaptability");

		ReportEntry entry = new ReportEntry(filePath.toString());
		populateReportEntry(entry);
		List<ReportEntry> entries = new ArrayList<>(1);
		entries.add(entry);

		return entries;
	}

	private void populateReportEntry(ReportEntry entry) {

		Document dom = getDom(entry.getFileName());

		entry.addVariable("isCorrectNamespace", isCorrectNamespace(dom));
		entry.addVariable("isExecutable", isExecutable(dom));

	}

	private Document getDom(String file) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		DocumentBuilder db;
		Document dom = null;

		try (FileInputStream fis = new FileInputStream(new File(file))) {
			db = dbf.newDocumentBuilder();
			db.setErrorHandler(new ErrorHandler() {

				@Override
				public void error(SAXParseException arg0) throws SAXException {
					throw arg0;
				}

				@Override
				public void fatalError(SAXParseException arg0)
						throws SAXException {
					throw arg0;
				}

				@Override
				public void warning(SAXParseException arg0) throws SAXException {
				}
			});

			dom = db.parse(fis);

		} catch (ParserConfigurationException | SAXException | IOException e) {

		}
		return dom;
	}

	private String isCorrectNamespace(Document dom) {
		Element root = null;
		try {
			root = dom.getDocumentElement();
		} catch (NullPointerException e) {
			return "false";
		}
		if ("http://www.omg.org/spec/BPMN/20100524/MODEL".equals(root
				.getNamespaceURI())) {
			return "true";
		} else {
			return "false";
		}
	}

	private String isExecutable(Document dom) {
		NodeList nodes = getChildrenOfDefinitions(dom);
		if (nodes == null) {
			return "false";
		}
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (node.getNodeName().contains("process")) {
				NamedNodeMap attributes = node.getAttributes();
				for (int j = 0; j < attributes.getLength(); j++) {
					Node attribute = attributes.item(j);
					if ("isExecutable".equals(attribute.getNodeName())
							&& "true".equals(attribute.getNodeValue())) {
						return "true";
					}
				}
			}
		}
		return "false";
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
			System.out.println(node.getNodeName());
			if (node.getNodeName().contains("definitions")) {
				return node.getChildNodes();
			}
		}
		return null;
	}
}
