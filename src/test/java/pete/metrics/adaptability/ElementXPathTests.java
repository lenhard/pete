package pete.metrics.adaptability;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;
import java.util.Map;

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

import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.InputSource;

public class ElementXPathTests {

	private static Map<String, AdaptableElement> elements;

	@BeforeClass
	public static void beforeClass() {
		System.setProperty("javax.xml.xpath.XPathFactory:"
				+ NamespaceConstant.OBJECT_MODEL_SAXON,
				"net.sf.saxon.xpath.XPathFactoryImpl");
		elements = new AdaptableElements().getElementsByName();
	}

	@Test
	public void noneStartEvent() {
		AdaptableElement noneStartEvent = elements.get("noneStartEvent");
		assertDetection(noneStartEvent,
				"src/test/resources/adaptability/ExecutableProcess.bpmn");
	}

	@Test
	public void loopTask() {
		AdaptableElement loopTask = elements.get("loopTask");
		assertNoFalsePositives(loopTask,
				"src/test/resources/adaptability/ExecutableProcess.bpmn");
		assertDetection(loopTask,
				"src/test/resources/adaptability/LoopTask.bpmn");
	}

	@Test
	public void userTask() {
		AdaptableElement userTask = elements.get("userTask");
		assertNoFalsePositives(userTask,
				"src/test/resources/adaptability/ExecutableProcess.bpmn");
		assertDetection(userTask,
				"src/test/resources/adaptability/UserTask.bpmn");
	}

	@Test
	public void globalUserTask() {
		AdaptableElement globalUserTask = elements.get("globalUserTask");
		assertDetection(globalUserTask,
				"src/test/resources/adaptability/GlobalUserTask.bpmn");
		assertNoFalsePositives(globalUserTask,
				"src/test/resources/adaptability/ExecutableProcess.bpmn");
	}

	@Test
	public void globalScriptTask() {
		AdaptableElement globalScriptTask = elements.get("globalScriptTask");
		assertDetection(globalScriptTask,
				"src/test/resources/adaptability/GlobalScriptTask.bpmn");
		assertNoFalsePositives(globalScriptTask,
				"src/test/resources/adaptability/GlobalUserTask.bpmn");
	}

	@Test
	public void manualTask() {
		AdaptableElement userTask = elements.get("manualTask");
		assertNoFalsePositives(userTask,
				"src/test/resources/adaptability/ExecutableProcess.bpmn");
		assertDetection(userTask,
				"src/test/resources/adaptability/ManualTask.bpmn");
	}

	@Test
	public void serviceTask() {
		AdaptableElement serviceTask = elements.get("serviceTask");
		assertDetection(serviceTask,
				"src/test/resources/adaptability/ExecutableProcess.bpmn");
		assertNoFalsePositives(serviceTask,
				"src/test/resources/adaptability/UserTask.bpmn");
	}

	@Test
	public void sendTask() {
		AdaptableElement sendTask = elements.get("sendTask");
		assertNoFalsePositives(sendTask,
				"src/test/resources/adaptability/ExecutableProcess.bpmn");
		assertDetection(sendTask,
				"src/test/resources/adaptability/SendTask.bpmn");
	}

	@Test
	public void scriptTask() {
		AdaptableElement scriptTask = elements.get("scriptTask");
		assertNoFalsePositives(scriptTask,
				"src/test/resources/adaptability/ExecutableProcess.bpmn");
		assertDetection(scriptTask,
				"src/test/resources/adaptability/ScriptTask.bpmn");
	}

	@Test
	public void receiveTask() {
		AdaptableElement receiveTask = elements.get("receiveTask");
		assertNoFalsePositives(receiveTask,
				"src/test/resources/adaptability/ExecutableProcess.bpmn");
		assertDetection(receiveTask,
				"src/test/resources/adaptability/ReceiveTask.bpmn");
	}

	@Test
	public void errorBoundaryEvent() {
		AdaptableElement noneStartEvent = elements.get("errorBoundaryEvent");
		assertNoFalsePositives(noneStartEvent,
				"src/test/resources/adaptability/ExecutableProcess.bpmn");
		assertDetection(noneStartEvent,
				"src/test/resources/adaptability/ErrorBoundaryEvent.bpmn");
	}

	@Test
	public void noneEndEvent() {
		AdaptableElement noneStartEvent = elements.get("noneEndEvent");
		assertNoFalsePositives(noneStartEvent,
				"src/test/resources/adaptability/ExecutableProcess.bpmn");
		assertDetection(noneStartEvent,
				"src/test/resources/adaptability/NoneEndEvent.bpmn");
	}

	private void assertDetection(AdaptableElement element, String fileLocation) {
		assertTrue("Element " + element.getName() + " was not found in "
				+ fileLocation, isContained(element, fileLocation));
	}

	private void assertNoFalsePositives(AdaptableElement element,
			String fileLocation) {
		assertFalse("Element " + element.getName() + " was found in "
				+ fileLocation, isContained(element, fileLocation));
	}

	private boolean isContained(AdaptableElement element, String fileLocation) {
		try {
			XPathFactory xpathFactory = XPathFactory
					.newInstance(NamespaceConstant.OBJECT_MODEL_SAXON);
			XPathEvaluator xpath = (XPathEvaluator) xpathFactory.newXPath();
			xpath.getConfiguration().setLineNumbering(true);
			xpath.setNamespaceContext(new BpmnNamespaceContext());
			XPathExpression expr = xpath
					.compile(element.getLocatorExpression());
			InputSource inputSource = new InputSource(
					new File(fileLocation).toString());
			SAXSource saxSource = new SAXSource(inputSource);
			NodeInfo doc = xpath.setSource(saxSource);

			@SuppressWarnings("unchecked")
			List<NodeInfo> matchedLines = (List<NodeInfo>) expr.evaluate(doc,
					XPathConstants.NODESET);
			if (matchedLines.size() > 0) {
				return true;
			}

			return false;
		} catch (XPathFactoryConfigurationException | XPathExpressionException
				| XPathException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
}
