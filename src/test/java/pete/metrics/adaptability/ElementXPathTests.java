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

import pete.metrics.adaptability.elements.AdaptableElement;
import pete.metrics.adaptability.elements.AdaptableElements;

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
	public void loopSubProcess() {
		AdaptableElement loopSubProcess = elements.get("loopSubProcess");
		assertNoFalsePositives(loopSubProcess,
				"src/test/resources/adaptability/LoopTask.bpmn");
		assertDetection(loopSubProcess,
				"src/test/resources/adaptability/LoopSubProcess.bpmn");
	}

	@Test
	public void subProcess() {
		AdaptableElement subProcess = elements.get("subProcess");
		assertNoFalsePositives(subProcess,
				"src/test/resources/adaptability/LoopSubProcess.bpmn");
		assertNoFalsePositives(subProcess,
				"src/test/resources/adaptability/SequentialMultiInstanceSubProcess.bpmn");
		assertDetection(subProcess,
				"src/test/resources/adaptability/SubProcess.bpmn");
	}

	@Test
	public void eventSubProcess() {
		AdaptableElement eventSubProcess = elements.get("eventSubProcess");
		assertNoFalsePositives(eventSubProcess,
				"src/test/resources/adaptability/AdHocSubProcess.bpmn");
		assertDetection(eventSubProcess,
				"src/test/resources/adaptability/EventSubProcess.bpmn");
	}

	@Test
	public void adHocSubProcess() {
		AdaptableElement loopTask = elements.get("adHocSubProcess");
		assertNoFalsePositives(loopTask,
				"src/test/resources/adaptability/ExecutableProcess.bpmn");
		assertDetection(loopTask,
				"src/test/resources/adaptability/AdHocSubProcess.bpmn");
	}

	@Test
	public void transactionSubProcess() {
		AdaptableElement loopTask = elements.get("transactionSubProcess");
		assertNoFalsePositives(loopTask,
				"src/test/resources/adaptability/ExecutableProcess.bpmn");
		assertDetection(loopTask,
				"src/test/resources/adaptability/TransactionSubProcess.bpmn");
	}

	@Test
	public void sequentialMultiInstanceTask() {
		AdaptableElement sequentialMultiInstanceTask = elements
				.get("sequentialMultiInstanceTask");
		assertNoFalsePositives(sequentialMultiInstanceTask,
				"src/test/resources/adaptability/ExecutableProcess.bpmn");
		assertDetection(sequentialMultiInstanceTask,
				"src/test/resources/adaptability/SequentialMultiInstanceTask.bpmn");
	}

	@Test
	public void parallelMultiInstanceTask() {
		AdaptableElement parallelMultiInstanceTask = elements
				.get("parallelMultiInstanceTask");
		assertNoFalsePositives(parallelMultiInstanceTask,
				"src/test/resources/adaptability/SequentialMultiInstanceTask.bpmn");
		assertDetection(parallelMultiInstanceTask,
				"src/test/resources/adaptability/ParallelMultiInstanceTask.bpmn");
	}

	@Test
	public void sequentialMultiInstanceSubProcess() {
		AdaptableElement sequentialMultiInstanceSubProcess = elements
				.get("sequentialMultiInstanceSubProcess");
		assertNoFalsePositives(sequentialMultiInstanceSubProcess,
				"src/test/resources/adaptability/SequentialMultiInstanceTask.bpmn");
		assertDetection(sequentialMultiInstanceSubProcess,
				"src/test/resources/adaptability/SequentialMultiInstanceSubProcess.bpmn");
	}

	@Test
	public void parallelMultiInstanceSubProcess() {
		AdaptableElement parallelMultiInstanceSubProcess = elements
				.get("parallelMultiInstanceSubProcess");
		assertNoFalsePositives(parallelMultiInstanceSubProcess,
				"src/test/resources/adaptability/SequentialMultiInstanceSubProcess.bpmn");
		assertDetection(parallelMultiInstanceSubProcess,
				"src/test/resources/adaptability/ParallelMultiInstanceSubProcess.bpmn");
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
	public void callActivityTask() {
		AdaptableElement callActivity = elements.get("callActivity");
		assertDetection(callActivity,
				"src/test/resources/adaptability/GlobalScriptTask.bpmn");
		assertNoFalsePositives(callActivity,
				"src/test/resources/adaptability/ParallelMultiInstanceSubProcess.bpmn");
	}

	@Test
	public void globalBusinessRuleTask() {
		AdaptableElement globalBusinessRuleTask = elements
				.get("globalBusinessRuleTask");
		assertDetection(globalBusinessRuleTask,
				"src/test/resources/adaptability/GlobalBusinessRuleTask.bpmn");
		assertNoFalsePositives(globalBusinessRuleTask,
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
	public void businessRuleTask() {
		AdaptableElement userTask = elements.get("businessRuleTask");
		assertNoFalsePositives(userTask,
				"src/test/resources/adaptability/ExecutableProcess.bpmn");
		assertDetection(userTask,
				"src/test/resources/adaptability/BusinessRuleTask.bpmn");
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
		AdaptableElement errorBoundaryEvent = elements
				.get("errorBoundaryEvent");
		assertNoFalsePositives(errorBoundaryEvent,
				"src/test/resources/adaptability/ExecutableProcess.bpmn");
		assertDetection(errorBoundaryEvent,
				"src/test/resources/adaptability/ErrorBoundaryEvent.bpmn");
		assertDetection(errorBoundaryEvent,
				"src/test/resources/adaptability/ErrorBoundaryEventEventDefinitionRef.bpmn");
	}

	@Test
	public void noneEndEvent() {
		AdaptableElement noneStartEvent = elements.get("noneEndEvent");
		assertNoFalsePositives(noneStartEvent,
				"src/test/resources/adaptability/ExecutableProcess.bpmn");
		assertDetection(noneStartEvent,
				"src/test/resources/adaptability/NoneEndEvent.bpmn");
	}

	@Test
	public void timerStartEvent() {
		AdaptableElement timerStartEvent = elements.get("timerStartEvent");
		assertNoFalsePositives(timerStartEvent,
				"src/test/resources/adaptability/MessageStartEvent.bpmn");
		assertDetection(timerStartEvent,
				"src/test/resources/adaptability/TimerStartEvent.bpmn");
	}

	@Test
	public void conditionalStartEvent() {
		AdaptableElement conditionalStartEvent = elements.get("conditionalStartEvent");
		assertNoFalsePositives(conditionalStartEvent,
				"src/test/resources/adaptability/MessageStartEvent.bpmn");
		assertDetection(conditionalStartEvent,
				"src/test/resources/adaptability/ConditionalStartEvent.bpmn");
	}

	@Test
	public void signalStartEvent() {
		AdaptableElement signalStartEvent = elements.get("signalStartEvent");
		assertNoFalsePositives(signalStartEvent,
				"src/test/resources/adaptability/MessageStartEvent.bpmn");
		assertNoFalsePositives(signalStartEvent,
				"src/test/resources/adaptability/MultipleStartEvent.bpmn");
		assertDetection(signalStartEvent,
				"src/test/resources/adaptability/SignalStartEvent.bpmn");
	}

	@Test
	public void multipleStartEvent() {
		AdaptableElement multipleStartEvent = elements.get("multipleStartEvent");
		assertNoFalsePositives(multipleStartEvent,
				"src/test/resources/adaptability/SignalStartEvent.bpmn");
		assertDetection(multipleStartEvent,
				"src/test/resources/adaptability/MultipleStartEvent.bpmn");
	}

	@Test
	public void multipleParallelStartEvent() {
		AdaptableElement multipleParallelStartEvent = elements.get("multipleParallelStartEvent");
		assertNoFalsePositives(multipleParallelStartEvent,
				"src/test/resources/adaptability/MultipleStartEvent.bpmn");
		assertDetection(multipleParallelStartEvent,
				"src/test/resources/adaptability/MultipleParallelStartEvent.bpmn");
	}

	@Test
	public void messageStartEvent() {
		AdaptableElement messageStartEvent = elements.get("messageStartEvent");
		assertNoFalsePositives(messageStartEvent,
				"src/test/resources/adaptability/TimerStartEvent.bpmn");
		assertDetection(messageStartEvent,
				"src/test/resources/adaptability/MessageStartEvent.bpmn");
	}

	@Test
	public void subProcessStartEvent() {
		AdaptableElement subProcess = elements.get("subProcessStartEvent");
		assertNoFalsePositives(subProcess,
				"src/test/resources/adaptability/ExecutableProcess.bpmn");
		assertDetection(subProcess,
				"src/test/resources/adaptability/SequentialMultiInstanceSubProcess.bpmn");
		assertDetection(subProcess,
				"src/test/resources/adaptability/SubProcess.bpmn");
	}

	@Test
	public void nonInterruptingMessageStartEvent() {
		AdaptableElement eventSubProcess = elements.get("nonInterruptingMesssageStartEvent");
		assertNoFalsePositives(eventSubProcess,
				"src/test/resources/adaptability/EventSubProcess.bpmn");
		assertDetection(eventSubProcess,
				"src/test/resources/adaptability/EventSubProcessNonInterruptingMessageStartEvent.bpmn");
	}

	@Test
	public void interruptingMessageStartEvent() {
		AdaptableElement eventSubProcess = elements.get("interruptingMesssageStartEvent");
		assertNoFalsePositives(eventSubProcess,
				"src/test/resources/adaptability/EventSubProcessNonInterruptingMessageStartEvent.bpmn");
		assertDetection(eventSubProcess,
				"src/test/resources/adaptability/EventSubProcessInterruptingMessageStartEvent.bpmn");
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
			e.printStackTrace();
			return false;
		}
	}
}
