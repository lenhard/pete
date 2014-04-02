package pete.metrics.adaptability;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class AdaptableElements {

	private final List<AdaptableElement> elements;

	public AdaptableElements() {
		elements = new ArrayList<>();
		elements.add(new AdaptableElement("activity"));
		elements.add(new AdaptableElement("adHocSubProcess"));
		elements.add(new AdaptableElement("boundaryEvent"));
		buildBusinessRuleTask();
		elements.add(new AdaptableElement("callActivity"));
		elements.add(new AdaptableElement("catchEvent"));
		elements.add(new AdaptableElement("complexGateway"));
		elements.add(new AdaptableElement("dataAssociation"));
		elements.add(new AdaptableElement("dataInput"));
		elements.add(new AdaptableElement("dataInputAssociation"));
		elements.add(new AdaptableElement("dataObject"));
		elements.add(new AdaptableElement("dataOutput"));
		elements.add(new AdaptableElement("dataOutputAssociation"));
		elements.add(new AdaptableElement("dataStore"));
		buildNoneEndEvent();
		elements.add(new AdaptableElement("endEvent"));
		elements.add(new AdaptableElement("error"));
		buildErrorBoundaryEvent();
		elements.add(new AdaptableElement("escalation"));
		elements.add(new AdaptableElement("event"));
		elements.add(new AdaptableElement("eventBasedGateway"));
		elements.add(new AdaptableElement("extension"));
		elements.add(new AdaptableElement("extensionElements"));
		elements.add(new AdaptableElement("formalExpression"));
		elements.add(new AdaptableElement("gateway"));
		elements.add(new AdaptableElement("globalBusinessRuleTask"));
		buildGlobalManualTask();
		buildGlobalScriptTask();
		buildGlobalUserTask();
		elements.add(new AdaptableElement("implicitThrowEvent"));
		elements.add(new AdaptableElement("inclusiveGateway"));
		elements.add(new AdaptableElement("inputSet"));
		elements.add(new AdaptableElement("intermediateCatchEvent"));
		elements.add(new AdaptableElement("intermediateThrowEvent"));
		buildManualTask();
		elements.add(new AdaptableElement("message"));
		elements.add(new AdaptableElement("messageFlow"));
		elements.add(new AdaptableElement("messageFlowAssociation"));
		elements.add(new AdaptableElement("multiInstanceLoopCharacteristics"));
		buildReceiveTask();
		buildScriptTask();
		buildSendTask();
		buildServiceTask();
		elements.add(new AdaptableElement("signal"));
		buildLoopTask();
		buildNoneStartEvent();
		elements.add(new AdaptableElement("subProcess"));
		buildTask();
		elements.add(new AdaptableElement("terminateEventDefinition"));
		elements.add(new AdaptableElement("throwEvent"));
		elements.add(new AdaptableElement("timerEventDefinition"));
		elements.add(new AdaptableElement("transaction"));
		buildUserTask();
	}

	private void buildBusinessRuleTask() {
		AdaptableElement businessRuleTask = new AdaptableElement(
				"businessRuleTask");
		// the tasks below can in principle be used to trigger (programmatically
		// or manually) the execution of a business rule through another program
		// and return the result
		businessRuleTask
		.setLocatorExpression("//*[local-name() = 'businessRuleTask']");
		businessRuleTask.addAdaption("serviceTask");
		businessRuleTask.addAdaption("userTask");
		businessRuleTask.addAdaption("scriptTask");
		businessRuleTask.addAdaption("manualTask");
		businessRuleTask.addAdaption("sendAndReceiveTask");
		businessRuleTask.addAdaption("globalScriptTask");
		businessRuleTask.addAdaption("globalUserTask");
		businessRuleTask.addAdaption("globalManualTask");

		elements.add(businessRuleTask);
	}

	private void buildGlobalManualTask() {
		AdaptableElement globalManualTask = new AdaptableElement(
				"globalManualTask");
		globalManualTask
		.setLocatorExpression("//*[local-name() = 'globalManualTask']");

		globalManualTask.addAdaption("serviceTask");
		globalManualTask.addAdaption("userTask");
		globalManualTask.addAdaption("scriptTask");
		globalManualTask.addAdaption("globalScriptTask");
		globalManualTask.addAdaption("manualTask");
		globalManualTask.addAdaption("globalUserTask");
		globalManualTask.addAdaption("sendTask");
		elements.add(globalManualTask);
	}

	private void buildManualTask() {
		AdaptableElement manualTask = new AdaptableElement("manualTask");
		manualTask.setLocatorExpression("//*[local-name() = 'manualTask']");
		// the tasks below can in principle be programmed to control a user
		// action

		manualTask.addAdaption("serviceTask");
		manualTask.addAdaption("userTask");
		manualTask.addAdaption("scriptTask");
		manualTask.addAdaption("globalScriptTask");
		manualTask.addAdaption("globalManualTask");
		manualTask.addAdaption("globalUserTask");
		manualTask.addAdaption("sendTask");
		elements.add(manualTask);
	}

	private void buildReceiveTask() {
		AdaptableElement receiveTask = new AdaptableElement("receiveTask");
		receiveTask.setLocatorExpression("//*[local-name() = 'receiveTask']");
		// all tasks below can in principle be used to wait for a message
		// (programmatically or manually)
		receiveTask.addAdaption("serviceTask");
		receiveTask.addAdaption("userTask");
		receiveTask.addAdaption("manualTask");
		receiveTask.addAdaption("scriptTask");
		receiveTask.addAdaption("globalScriptTask");
		receiveTask.addAdaption("globalManualTask");
		receiveTask.addAdaption("globalUserTask");
		receiveTask.addAdaption("messageEvent");

		elements.add(receiveTask);
	}

	private void buildScriptTask() {
		AdaptableElement scriptTask = new AdaptableElement("scriptTask");
		scriptTask.setLocatorExpression("//*[local-name() = 'scriptTask']");
		// all tasks below can in principle be used to trigger the execution of
		// a script at some entity. A receiveTask is not suitable as it is
		// passively waiting and a businessRuleTask is too specific
		scriptTask.addAdaption("serviceTask");
		scriptTask.addAdaption("sendTask");
		scriptTask.addAdaption("userTask");
		scriptTask.addAdaption("manualTask");
		scriptTask.addAdaption("scriptTask");
		scriptTask.addAdaption("globalManualTask");
		scriptTask.addAdaption("globalUserTask");

		elements.add(scriptTask);
	}

	private void buildGlobalScriptTask() {
		AdaptableElement globalScriptTask = new AdaptableElement(
				"globalScriptTask");
		globalScriptTask
		.setLocatorExpression("//*[local-name() = 'globalScriptTask']");

		// all tasks below can in principle be used to trigger the execution of
		// a script at some entity. A receiveTask is not suitable as it is
		// passively waiting and a businessRuleTask is too specific
		globalScriptTask.addAdaption("serviceTask");
		globalScriptTask.addAdaption("sendTask");
		globalScriptTask.addAdaption("userTask");
		globalScriptTask.addAdaption("manualTask");
		globalScriptTask.addAdaption("globalScriptTask");
		globalScriptTask.addAdaption("globalManualTask");
		globalScriptTask.addAdaption("globalUserTask");
		elements.add(globalScriptTask);
	}

	private void buildSendTask() {
		AdaptableElement sendTask = new AdaptableElement("sendTask");
		sendTask.setLocatorExpression("//*[local-name() = 'sendTask']");
		// all tasks below can in principle be used to trigger the sending of a
		// message. A receiveTask is not suitable as it is passively waiting
		// and a businessRuleTask is too specific
		sendTask.addAdaption("serviceTask");
		sendTask.addAdaption("scriptTask");
		sendTask.addAdaption("userTask");
		sendTask.addAdaption("manualTask");
		sendTask.addAdaption("globalScriptTask");
		sendTask.addAdaption("globalManualTask");
		sendTask.addAdaption("globalUserTask");

		elements.add(sendTask);
	}

	private void buildServiceTask() {
		AdaptableElement serviceTask = new AdaptableElement("serviceTask");
		serviceTask.setLocatorExpression("//*[local-name() = 'serviceTask']");
		// all tasks below can in principle be used to trigger service
		// execution. A receiveTask is not suitable as it is passively waiting
		// and a businessRuleTask is too specific
		serviceTask.addAdaption("scriptTask");
		serviceTask.addAdaption("manualTask");
		serviceTask.addAdaption("userTask");
		serviceTask.addAdaption("sendTask");
		serviceTask.addAdaption("globalScriptTask");
		serviceTask.addAdaption("globalManualTask");
		serviceTask.addAdaption("globalUserTask");

		elements.add(serviceTask);
	}

	private void buildUserTask() {
		AdaptableElement userTask = new AdaptableElement("userTask");
		userTask.setLocatorExpression("//*[local-name() = 'userTask']");
		// the tasks below can in principle be programmed to ask for user input,
		// an hence are an adaption for userTask
		userTask.addAdaption("manualTask");
		userTask.addAdaption("scriptTask");
		userTask.addAdaption("serviceTask");
		userTask.addAdaption("sendTask");
		userTask.addAdaption("globalScriptTask");
		userTask.addAdaption("globalManualTask");
		userTask.addAdaption("globalUserTask");
		elements.add(userTask);
	}

	private void buildGlobalUserTask() {
		AdaptableElement globalUserTask = new AdaptableElement("globalUserTask");

		globalUserTask
		.setLocatorExpression("/*[local-name() = 'globalUserTask']");
		// the tasks below can in principle be programmed to ask for user input,
		// an hence are an adaption for userTask
		globalUserTask.addAdaption("manualTask");
		globalUserTask.addAdaption("scriptTask");
		globalUserTask.addAdaption("serviceTask");
		globalUserTask.addAdaption("sendTask");
		globalUserTask.addAdaption("globalScriptTask");
		globalUserTask.addAdaption("globalManualTask");
		globalUserTask.addAdaption("userTask");
		elements.add(globalUserTask);
	}

	private void buildLoopTask() {
		AdaptableElement loopTask = new AdaptableElement("loopTask");
		loopTask.setLocatorExpression("//*[(local-name() = 'sendTask' or local-name() = 'receiveTask' or local-name() = 'serviceTask' or local-name() = 'manualTask' or local-name() = 'businessRuleTask' or local-name() = 'userTask' or local-name() = 'sendTask' or local-name() = 'globalUserTask' or local-name() = 'globalManualTask' or local-name() = ' globalScriptTask' or local-name() = 'globalBusinessRuleTask') and (child::*[local-name() = 'standardLoopCharacteristics'])]");
		loopTask.addAdaption("exclusiveGatewaysAndSequenceFlows");
		loopTask.addAdaption("loopSubProcess");
		loopTask.addAdaption("adHocSubProcess");
		loopTask.addAdaption("eventSubProcess");
		elements.add(loopTask);
	}

	private void buildErrorBoundaryEvent() {
		AdaptableElement errorBoundaryEvent = new AdaptableElement(
				"errorBoundaryEvent");
		errorBoundaryEvent
		.setLocatorExpression("//*[local-name() = 'boundaryEvent' and (child::*[local-name() = 'errorEventDefinition'])]");
		errorBoundaryEvent.addAdaption("messageBoundaryEvent");
		errorBoundaryEvent.addAdaption("escalationBoundaryEvent");
		errorBoundaryEvent.addAdaption("conditionalBoundaryEvent");
		errorBoundaryEvent.addAdaption("signalBoundaryEvent");
		errorBoundaryEvent.addAdaption("multipleBoundaryEvent");
		errorBoundaryEvent.addAdaption("multipleParallelBoundaryEvent");
		elements.add(errorBoundaryEvent);
	}

	private void buildTask() {
		AdaptableElement task = new AdaptableElement("task");
		task.setLocatorExpression("//*[local-name() = 'task' or local-name() = 'globalTask']");
		task.addAdaption("sendTask");
		task.addAdaption("serviceTask");
		task.addAdaption("manualTask");
		task.addAdaption("businessRuleTask");
		task.addAdaption("userTask");
		task.addAdaption("scriptTask");
		task.addAdaption("receiveTask");
		task.addAdaption("globalUserTask");
		task.addAdaption("globalManualTask");
		task.addAdaption("globalScriptTask");
		task.addAdaption("globalBusinessRuleTask");
		elements.add(task);
	}

	private void buildNoneEndEvent() {
		AdaptableElement noneEndEvent = new AdaptableElement("noneEndEvent");
		noneEndEvent
		.setLocatorExpression("/*[local-name() = 'process']/*[local-name() = 'endEvent' and not(child::*[contains(local-name(),'EventDefinition')])]");
		noneEndEvent.addAdaption("messageEndEvent");
		noneEndEvent.addAdaption("signalEndEvent");
		noneEndEvent.addAdaption("terminateEndEvent");
		noneEndEvent.addAdaption("multipleEndEvent");
		elements.add(noneEndEvent);
	}

	private void buildNoneStartEvent() {
		AdaptableElement noneStartEvent = new AdaptableElement("noneStartEvent");
		noneStartEvent
		.setLocatorExpression("/*[local-name() = 'process']/*[local-name() = 'startEvent' and not(/*[contains(local-name(),'EventDefinition')])]");
		noneStartEvent.addAdaption("messageStartEvent");
		noneStartEvent.addAdaption("conditionalStartEvent");
		noneStartEvent.addAdaption("signalStartEvent");
		noneStartEvent.addAdaption("multipleStartEvent");
		noneStartEvent.addAdaption("parallelMultipleStartEvent");
		elements.add(noneStartEvent);
	}

	public List<String> getElementNames() {
		List<String> result = new ArrayList<>(elements.size());
		elements.forEach(element -> result.add(element.getName()));
		return result;
	}

	public List<AdaptableElement> getElements() {
		List<AdaptableElement> result = new ArrayList<>(elements.size());
		elements.forEach(element -> result.add(element));
		return result;
	}

	public Map<String, AdaptableElement> getElementsByName() {
		HashMap<String, AdaptableElement> result = new HashMap<>();
		elements.forEach(element -> result.put(element.getName(), element));
		return result;
	}
}
