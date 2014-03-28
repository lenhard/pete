package pete.metrics.adaptability;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import pete.executables.AnalysisException;
import pete.executables.FileAnalyzer;
import pete.reporting.ReportEntry;

public class AdaptabilityAnalyzer implements FileAnalyzer {

	private ElementCounter elementCounter;

	private BpmnInspector inspector;

	private AdaptabilityMetric metric;

	public AdaptabilityAnalyzer() {
		// Currently always do strict parsing
		elementCounter = new ElementCounter();
		inspector = new BpmnInspector();
		metric = new AdaptabilityMetric();
	}

	@Override
	public List<ReportEntry> analyzeFile(Path filePath) {
		System.out.println("Analyzing " + filePath + " for adaptability");

		ReportEntry entry = new ReportEntry(filePath.toString());
		try {
			populateReportEntry(entry);
		} catch (AnalysisException e) {
			System.err.println(e.getMessage());
			return new ArrayList<>(0);
		}
		List<ReportEntry> entries = new ArrayList<>(1);
		entries.add(entry);

		return entries;
	}

	private void populateReportEntry(ReportEntry entry) {

		Document dom = getDom(entry.getFileName());

		entry.addVariable("isCorrectNamespace",
				inspector.isCorrectNamespace(dom));
		Node process = inspector.getProcess(dom);
		entry.addVariable("isExecutable", inspector.isExecutable(process));
		entry.addVariable("elements", inspector.getNumberOfChildren(process)
				+ "");

		elementCounter.addToCounts(process);
		entry.addVariable("AD",
				metric.computeAdaptability(elementCounter.getElementNumbers())
						+ "");
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

	@Override
	public void traversalCompleted() {
		elementCounter.writeToCsv(Paths.get("raw.csv"));
	}
}
