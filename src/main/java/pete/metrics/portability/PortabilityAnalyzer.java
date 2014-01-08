package pete.metrics.portability;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.sax.SAXSource;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactoryConfigurationException;

import net.sf.saxon.lib.NamespaceConstant;
import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.trans.XPathException;
import net.sf.saxon.xpath.XPathEvaluator;

import org.xml.sax.InputSource;

import pete.executables.AnalysisException;
import pete.executables.FileAnalyzer;
import pete.metrics.portability.assertions.BpelNamespaceContext;
import pete.metrics.portability.assertions.TestAssertion;
import pete.metrics.portability.assertions.TestAssertions;
import pete.reporting.ReportEntry;

public class PortabilityAnalyzer implements FileAnalyzer {

	private NodeInfo doc;

	private XPathEvaluator xpath;

	private AnalysisResult result;

	private void createRawResult(Path filePath) {
		result = new AnalysisResult();
		result.setBpelFile(filePath);
	}

	private void createXPathEvaluator()
			throws XPathFactoryConfigurationException {
		XPathFactory xpathFactory = XPathFactory
				.newInstance(NamespaceConstant.OBJECT_MODEL_SAXON);
		xpath = (XPathEvaluator) xpathFactory.newXPath();
		xpath.getConfiguration().setLineNumbering(true);
		xpath.setNamespaceContext(new BpelNamespaceContext());
	}

	private void createSourceDocument(String filePath) throws XPathException {
		InputSource inputSource = new InputSource(new File(filePath).toString());
		SAXSource saxSource = new SAXSource(inputSource);
		doc = xpath.setSource(saxSource);
	}

	private AnalysisResult analyze() {
		List<TestAssertion> assertions = new TestAssertions().createAll();

		for (TestAssertion assertion : assertions) {
			check(assertion);
		}

		computeElementNumber();
		computeActivityNumber();
		computeServiceActivityNumber();

		return result;
	}

	private void computeElementNumber() {
		try {
			XPathExpression expr = xpath
					.compile("//*[not(ancestor::*[local-name() = 'literal']) and not(ancestor::*[local-name() = 'query'])]");
			@SuppressWarnings("unchecked")
			List<NodeInfo> matchedNodes = (List<NodeInfo>) expr.evaluate(doc,
					XPathConstants.NODESET);
			int numOfElements = matchedNodes.size();
			result.setNumberOfElements(numOfElements);
		} catch (XPathExpressionException e) {
			throw new AnalysisException(e);
		}
	}

	private void computeActivityNumber() {
		try {
			XPathExpression expr = xpath
					.compile("//*[not(ancestor::*[local-name() = 'literal']) and not(ancestor::*[local-name() = 'query']) "
							+ "and (local-name() = 'invoke' or local-name() = 'receive' or local-name() = 'reply' "
							+ "or local-name() = 'assign' or local-name() = 'throw' or local-name() = 'wait' "
							+ "or local-name() = 'empty' or local-name() = 'exit' or local-name() = 'rethrow' "
							+ "or local-name() = 'sequence' or local-name() = 'if' or local-name() = 'while' "
							+ "or local-name() = 'repeatUntil' or local-name() = 'pick' "
							+ "or local-name() = 'flow' or local-name() = 'forEach' or local-name() = 'scope' "
							+ "or local-name() = 'onEvent' or local-name() = 'onAlarm' or local-name() = 'compensationHandler' "
							+ "or local-name() = 'catch' or local-name() = 'catchAll' or local-name() = 'terminationHandler' "
							+ "or local-name() = 'compensate' or local-name() = 'compensateScope')]");
			@SuppressWarnings("unchecked")
			List<NodeInfo> matchedNodes = (List<NodeInfo>) expr.evaluate(doc,
					XPathConstants.NODESET);
			int numOfActivities = matchedNodes.size();
			result.setNumberOfActivities(numOfActivities);
		} catch (XPathExpressionException e) {
			throw new AnalysisException(e);
		}
	}

	private void computeServiceActivityNumber() {
		try {
			XPathExpression expr = xpath
					.compile("//*[not(ancestor::*[local-name() = 'literal']) and not(ancestor::*[local-name() = 'query']) "
							+ "and (local-name() = 'invoke' or local-name() = 'receive' or local-name() = 'reply' "
							+ "or local-name() = 'onMessage')]");
			@SuppressWarnings("unchecked")
			List<NodeInfo> matchedNodes = (List<NodeInfo>) expr.evaluate(doc,
					XPathConstants.NODESET);
			int numOfActivities = matchedNodes.size();
			result.setNumberOfServiceActivities(numOfActivities);
		} catch (XPathExpressionException e) {
			throw new AnalysisException(e);
		}
	}

	private void check(TestAssertion assertion) {
		try {
			XPathExpression expr = xpath.compile(assertion.getTarget());
			@SuppressWarnings("unchecked")
			List<NodeInfo> matchedLines = (List<NodeInfo>) expr.evaluate(doc,
					XPathConstants.NODESET);
			for (NodeInfo node : matchedLines) {
				addViolation(assertion, node);
			}
		} catch (XPathExpressionException e) {
			throw new AnalysisException(
					"Assertion " + assertion.getId() + ": ", e);
		}
	}

	private void addViolation(TestAssertion assertion, NodeInfo node) {
		NodeInfo parent = node.getParent();
		NodeInfo grandParent = parent.getParent();

		int grandParentLineNumber = 0;
		if (grandParent != null) {
			grandParentLineNumber = grandParent.getLineNumber();
		}

		result.addViolation(assertion, node.getLineNumber(),
				parent.getLineNumber(), grandParentLineNumber);
	}

	@Override
	public List<ReportEntry> analyzeFile(Path filePath) {
		reset(filePath);

		System.out.println("Analyzing " + filePath + " for portability");
		ReportEntry entry = populateEntry(filePath);

		List<ReportEntry> report = new ArrayList<ReportEntry>();
		report.add(entry);
		return report;
	}

	private ReportEntry populateEntry(Path filePath) {
		bpp.executables.FileAnalyzer bppAnalyzer = new bpp.executables.FileAnalyzer(
				filePath.toString());
		bpp.domain.AnalysisResult analysisResult = bppAnalyzer.analyze();
		ReportEntry entry = new ReportEntry(analysisResult.getBpelFile()
				.toString());
		entry.addVariable("class", analysisResult.getClassification()
				.toString());
		entry.addVariable("N", analysisResult.getNumberOfElements() + "");
		entry.addVariable(BasicPortabilityMetric.getLabel(),
				analysisResult.getBasicPortabilityMetric() + "");
		entry.addVariable(WeightedElementsPortabilityMetric.getLabel(),
				analysisResult.getWeightedElementsMetric() + "");
		entry.addVariable(ActivityPortabilityMetric.getLabel(),
				analysisResult.getActivityPortabilityMetric() + "");
		entry.addVariable(ServiceCommunicationPortabilityMetric.getLabel(),
				analysisResult.getServiceCommunicationPortabilityMetric() + "");
		return entry;
	}

	private void reset(Path filePath) {
		createRawResult(filePath);
		try {
			createXPathEvaluator();
			createSourceDocument(filePath.toString());
		} catch (XPathFactoryConfigurationException | XPathException e) {
			throw new AnalysisException(e);
		}
	}
}
