package test.pete.metrics.adaptability;

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

import pete.metrics.adaptability.AdaptableElement;
import pete.metrics.adaptability.BpmnNamespaceContext;
import pete.metrics.adaptability.RelevantElements;

public class ElementXPathTests {

	private static Map<String, AdaptableElement> elements;

	@BeforeClass
	public static void beforeClass() {
		System.setProperty("javax.xml.xpath.XPathFactory:"
				+ NamespaceConstant.OBJECT_MODEL_SAXON,
				"net.sf.saxon.xpath.XPathFactoryImpl");
		elements = new RelevantElements().getElementsByName();
	}

	@Test
	public void noneStartEvent() {
		AdaptableElement noneStartEvent = elements.get("noneStartEvent");
		assertTrue(isContained(noneStartEvent,
				"src/test/resources/adaptability/ExecutableProcess.bpmn"));
	}

	@Test
	public void loopTask() {
		AdaptableElement loopTask = elements.get("loopTask");
		assertFalse(isContained(loopTask,
				"src/test/resources/adaptability/ExecutableProcess.bpmn"));
		assertTrue(isContained(loopTask,
				"src/test/resources/adaptability/LoopTask.bpmn"));
	}

	@Test
	public void userTask() {
		AdaptableElement userTask = elements.get("userTask");
		assertFalse(isContained(userTask,
				"src/test/resources/adaptability/ExecutableProcess.bpmn"));
		assertTrue(isContained(userTask,
				"src/test/resources/adaptability/UserTask.bpmn"));
	}

	@Test
	public void serviceTask() {
		AdaptableElement serviceTask = elements.get("serviceTask");
		assertTrue(isContained(serviceTask,
				"src/test/resources/adaptability/ExecutableProcess.bpmn"));
		assertFalse(isContained(serviceTask,
				"src/test/resources/adaptability/UserTask.bpmn"));
	}

	@Test
	public void sendTask() {
		AdaptableElement sendTask = elements.get("sendTask");
		assertFalse(isContained(sendTask,
				"src/test/resources/adaptability/ExecutableProcess.bpmn"));
		assertTrue(isContained(sendTask,
				"src/test/resources/adaptability/SendTask.bpmn"));
	}

	@Test
	public void errorBoundaryEvent() {
		AdaptableElement noneStartEvent = elements.get("errorBoundaryEvent");
		assertFalse(isContained(noneStartEvent,
				"src/test/resources/adaptability/ExecutableProcess.bpmn"));
		assertTrue(isContained(noneStartEvent,
				"src/test/resources/adaptability/ErrorBoundaryEvent.bpmn"));
	}

	@Test
	public void noneEndEvent() {
		AdaptableElement noneStartEvent = elements.get("noneEndEvent");
		assertFalse(isContained(noneStartEvent,
				"src/test/resources/adaptability/ExecutableProcess.bpmn"));
		assertTrue(isContained(noneStartEvent,
				"src/test/resources/adaptability/NoneEndEvent.bpmn"));
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
