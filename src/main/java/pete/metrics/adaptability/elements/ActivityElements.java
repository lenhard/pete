package pete.metrics.adaptability.elements;


class ActivityElements extends ElementsCollection{

	public ActivityElements() {
		buildOrdinarySubProcess();
		buildBusinessRuleTask();
		buildCallActivity();
		buildGlobalBusinessRuleTask();
		buildGlobalManualTask();
		buildGlobalScriptTask();
		buildGlobalUserTask();
		buildManualTask();
		buildSequentialMultiInstanceTask();
		buildParallelMultiInstanceTask();
		buildReceiveTask();
		buildScriptTask();
		buildSendTask();
		buildServiceTask();
		buildUserTask();
		buildLoopTask();
		buildLoopSubProcess();
		buildSequentialMultInstanceSubProcess();
		buildParallelMultInstanceSubProcess();
		buildAdHocSubprocess();
		buildTransactionSubProcess();
		buildEventSubProcess();
		buildTask();
	}

	private void buildCallActivity() {
		AdaptableElement callActivity = new AdaptableElement("callActivity");
		callActivity.setLocatorExpression("//*[local-name() = 'callActivity']");
		callActivity
		.setDocumentation("A callActivity can be adapted by replacing it with the called globalActivity or process");
		callActivity.addAdaption("embedProcess");
		callActivity.addAdaption("embedUserTask");
		callActivity.addAdaption("embedManualTask");
		callActivity.addAdaption("embedScriptTask");
		callActivity.addAdaption("embedBusinessRuleTask");

		add(callActivity);
	}

	private void buildOrdinarySubProcess() {
		AdaptableElement subProcess = new AdaptableElement("subProcess");
		subProcess
		.setLocatorExpression("//*[local-name() = 'subProcess' and not (@triggeredByEvent = 'true' or child::*[local-name() = 'multiInstanceLoopCharacteristics'] or child::*[local-name() = 'standardLoopCharacteristics'])]");
		subProcess
		.setDocumentation("An ordinary subProcess can be embedded into the process or replaced by a different type of subprocess");
		subProcess.addAdaption("embeddIntoProcess");
		subProcess.addAdaption("transactionSubProcess");
		subProcess.addAdaption("eventSubProcess");
		subProcess.addAdaption("adHocSubProcess");

		add(subProcess);
	}

	private void buildEventSubProcess() {
		AdaptableElement eventSubProcess = new AdaptableElement(
				"eventSubProcess");
		eventSubProcess
		.setLocatorExpression("//*[local-name() = 'subProcess' and @triggeredByEvent = 'true']");
		eventSubProcess
		.setDocumentation("EventSubProcesses can be adapted to a different form of subProcess that is executed through a callActivity.");
		eventSubProcess.addAdaption("callActivityAndTransactionSubProcess");
		eventSubProcess.addAdaption("callActiviyAndAdHocSubProcess");
		eventSubProcess.addAdaption("callActivityAndOrdinarySubProcess");

		add(eventSubProcess);
	}

	private void buildTransactionSubProcess() {
		AdaptableElement transactionSubProcess = new AdaptableElement(
				"transactionSubProcess");
		transactionSubProcess
		.setLocatorExpression("//*[local-name() = 'transaction']");
		transactionSubProcess
		.setDocumentation("A transactional context cannot be emulated with any other element in BPMN");

		add(transactionSubProcess);
	}

	private void buildAdHocSubprocess() {
		AdaptableElement adHocSubProcess = new AdaptableElement(
				"adHocSubProcess");
		adHocSubProcess
		.setLocatorExpression("//*[local-name() = 'adHocSubProcess']");
		adHocSubProcess
		.setDocumentation("Due to their unstructured nature, no general advice can be given on how to adapt an adHocSubProcess");

		add(adHocSubProcess);
	}

	private void buildSequentialMultInstanceSubProcess() {
		AdaptableElement sequentialMultiInstanceSubProcess = new AdaptableElement(
				"sequentialMultiInstanceSubProcess");
		sequentialMultiInstanceSubProcess
		.setLocatorExpression("//*[(local-name() = 'subProcess') "
				+ "and (child::*[local-name() = 'multiInstanceLoopCharacteristics' and @isSequential='true'])]");
		sequentialMultiInstanceSubProcess
		.setDocumentation("Sequential multi-instance subprocesses can be embedded in code and surrounded by ordinary looping mechanisms or adapted to different types of subprocesses");
		sequentialMultiInstanceSubProcess
		.addAdaption("embeddedfragmentWithExclusiveGateways");
		sequentialMultiInstanceSubProcess
		.addAdaption("embeddedfragmentWithComplexGateways");
		sequentialMultiInstanceSubProcess.addAdaption("eventSubProcess");
		sequentialMultiInstanceSubProcess.addAdaption("adHocSubprocess");
		sequentialMultiInstanceSubProcess.addAdaption("loopSubProcess");

		add(sequentialMultiInstanceSubProcess);
	}

	private void buildParallelMultInstanceSubProcess() {
		AdaptableElement sequentialMultiInstanceSubProcess = new AdaptableElement(
				"parallelMultiInstanceSubProcess");
		sequentialMultiInstanceSubProcess
		.setLocatorExpression("//*[(local-name() = 'subProcess') "
				+ "and (child::*[local-name() = 'multiInstanceLoopCharacteristics' and @isSequential='false'])]");
		sequentialMultiInstanceSubProcess
		.setDocumentation("Parallel multi-instance subprocesses can be embedded in code and surrounded by looping mechanisms along with parallelism or adapted to a different type of subprocess");
		sequentialMultiInstanceSubProcess
		.addAdaption("embeddedfragmentWithExclusiveGateways");
		sequentialMultiInstanceSubProcess
		.addAdaption("embeddedfragmentWithComplexGateways");
		sequentialMultiInstanceSubProcess.addAdaption("adHocSubprocess");

		add(sequentialMultiInstanceSubProcess);
	}

	private void buildLoopSubProcess() {
		AdaptableElement loopSubProcess = new AdaptableElement("loopSubProcess");
		loopSubProcess
		.setLocatorExpression("//*[(local-name() = 'subProcess') "
				+ "and (child::*[local-name() = 'standardLoopCharacteristics'])]");
		loopSubProcess
		.setDocumentation("Looping subprocesses can be embedded in code and surrounded by ordinary looping mechanisms or adapted to different types of subprocesses.");
		loopSubProcess.addAdaption("embeddedfragmentWithExclusiveGateways");
		loopSubProcess.addAdaption("embeddedfragmentWithComplexGateways");
		loopSubProcess.addAdaption("eventSubProcess");
		loopSubProcess.addAdaption("adHocSubprocess");

		add(loopSubProcess);
	}

	private void buildGlobalBusinessRuleTask() {
		AdaptableElement globalBusinessRuleTask = new AdaptableElement(
				"globalBusinessRuleTask");

		globalBusinessRuleTask
		.setLocatorExpression("//*[local-name() = 'globalBusinessRuleTask']");
		globalBusinessRuleTask
		.setDocumentation("the adaptions can in principle be used to trigger (programmatically or manually) the execution of a business rule through another program and return the result");
		globalBusinessRuleTask.addAdaption("serviceTask");
		globalBusinessRuleTask.addAdaption("userTask");
		globalBusinessRuleTask.addAdaption("scriptTask");
		globalBusinessRuleTask.addAdaption("manualTask");
		globalBusinessRuleTask.addAdaption("businessRuleTask");
		globalBusinessRuleTask.addAdaption("sendAndReceiveTask");
		globalBusinessRuleTask.addAdaption("globalScriptTask");
		globalBusinessRuleTask.addAdaption("globalUserTask");
		globalBusinessRuleTask.addAdaption("globalManualTask");

		add(globalBusinessRuleTask);
	}

	private void buildBusinessRuleTask() {
		AdaptableElement businessRuleTask = new AdaptableElement(
				"businessRuleTask");

		businessRuleTask
		.setDocumentation("the adaptions can in principle be used to trigger (programmatically or manually) the execution of a business rule through another program and return the result");
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
		businessRuleTask.addAdaption("globalBusinessRuleTask");

		add(businessRuleTask);
	}

	private void buildGlobalManualTask() {
		AdaptableElement globalManualTask = new AdaptableElement(
				"globalManualTask");
		globalManualTask
		.setLocatorExpression("//*[local-name() = 'globalManualTask']");

		globalManualTask
		.setDocumentation("the adaptions can in principle be programmed to control a user action");
		globalManualTask.addAdaption("serviceTask");
		globalManualTask.addAdaption("userTask");
		globalManualTask.addAdaption("scriptTask");
		globalManualTask.addAdaption("globalScriptTask");
		globalManualTask.addAdaption("manualTask");
		globalManualTask.addAdaption("globalUserTask");
		globalManualTask.addAdaption("sendTask");
		add(globalManualTask);
	}

	private void buildManualTask() {
		AdaptableElement manualTask = new AdaptableElement("manualTask");
		manualTask.setLocatorExpression("//*[local-name() = 'manualTask']");

		manualTask
		.setDocumentation("the adaptions can in principle be programmed to control a user action");
		manualTask.addAdaption("serviceTask");
		manualTask.addAdaption("userTask");
		manualTask.addAdaption("scriptTask");
		manualTask.addAdaption("globalScriptTask");
		manualTask.addAdaption("globalManualTask");
		manualTask.addAdaption("globalUserTask");
		manualTask.addAdaption("sendTask");
		add(manualTask);
	}

	private void buildReceiveTask() {
		AdaptableElement receiveTask = new AdaptableElement("receiveTask");
		receiveTask.setLocatorExpression("//*[local-name() = 'receiveTask']");

		receiveTask
		.setDocumentation("the adaptions can in principle be used to wait for a message (programmatically or manually)");
		receiveTask.addAdaption("serviceTask");
		receiveTask.addAdaption("userTask");
		receiveTask.addAdaption("manualTask");
		receiveTask.addAdaption("scriptTask");
		receiveTask.addAdaption("globalScriptTask");
		receiveTask.addAdaption("globalManualTask");
		receiveTask.addAdaption("globalUserTask");
		receiveTask.addAdaption("messageEvent");

		add(receiveTask);
	}

	private void buildScriptTask() {
		AdaptableElement scriptTask = new AdaptableElement("scriptTask");
		scriptTask.setLocatorExpression("//*[local-name() = 'scriptTask']");

		scriptTask
		.setDocumentation("the adaptions can in principle be used to trigger the execution of a script at some entity. A receiveTask is not suitable as it is passively waiting and a businessRuleTask is too specific");
		scriptTask.addAdaption("serviceTask");
		scriptTask.addAdaption("sendTask");
		scriptTask.addAdaption("userTask");
		scriptTask.addAdaption("manualTask");
		scriptTask.addAdaption("scriptTask");
		scriptTask.addAdaption("globalManualTask");
		scriptTask.addAdaption("globalUserTask");

		add(scriptTask);
	}

	private void buildGlobalScriptTask() {
		AdaptableElement globalScriptTask = new AdaptableElement(
				"globalScriptTask");
		globalScriptTask
		.setLocatorExpression("//*[local-name() = 'globalScriptTask']");

		globalScriptTask
		.setDocumentation("the adaptions can in principle be used to trigger the execution of a script at some entity. A receiveTask is not suitable as it is passively waiting and a businessRuleTask is too specific");
		globalScriptTask.addAdaption("serviceTask");
		globalScriptTask.addAdaption("sendTask");
		globalScriptTask.addAdaption("userTask");
		globalScriptTask.addAdaption("manualTask");
		globalScriptTask.addAdaption("globalScriptTask");
		globalScriptTask.addAdaption("globalManualTask");
		globalScriptTask.addAdaption("globalUserTask");
		add(globalScriptTask);
	}

	private void buildSendTask() {
		AdaptableElement sendTask = new AdaptableElement("sendTask");
		sendTask.setLocatorExpression("//*[local-name() = 'sendTask']");

		sendTask.setDocumentation("the adaptions can in principle be used to trigger the sending of a message. A receiveTask is not suitable as it is passively waiting and a businessRuleTask is too specific");
		sendTask.addAdaption("serviceTask");
		sendTask.addAdaption("scriptTask");
		sendTask.addAdaption("userTask");
		sendTask.addAdaption("manualTask");
		sendTask.addAdaption("globalScriptTask");
		sendTask.addAdaption("globalManualTask");
		sendTask.addAdaption("globalUserTask");

		add(sendTask);
	}

	private void buildServiceTask() {
		AdaptableElement serviceTask = new AdaptableElement("serviceTask");
		serviceTask.setLocatorExpression("//*[local-name() = 'serviceTask']");

		serviceTask
		.setDocumentation("the adaptions can in principle be used to trigger service execution. A receiveTask is not suitable as it is passively waiting and a businessRuleTask is too specific");
		serviceTask.addAdaption("scriptTask");
		serviceTask.addAdaption("manualTask");
		serviceTask.addAdaption("userTask");
		serviceTask.addAdaption("sendTask");
		serviceTask.addAdaption("globalScriptTask");
		serviceTask.addAdaption("globalManualTask");
		serviceTask.addAdaption("globalUserTask");

		add(serviceTask);
	}

	private void buildUserTask() {
		AdaptableElement userTask = new AdaptableElement("userTask");
		userTask.setLocatorExpression("//*[local-name() = 'userTask']");

		userTask.setDocumentation("the adaptions can in principle be programmed to ask for user input, and hence are an adaption for userTask");
		userTask.addAdaption("manualTask");
		userTask.addAdaption("scriptTask");
		userTask.addAdaption("serviceTask");
		userTask.addAdaption("sendTask");
		userTask.addAdaption("globalScriptTask");
		userTask.addAdaption("globalManualTask");
		userTask.addAdaption("globalUserTask");
		add(userTask);
	}

	private void buildGlobalUserTask() {
		AdaptableElement globalUserTask = new AdaptableElement("globalUserTask");

		globalUserTask
		.setLocatorExpression("/*[local-name() = 'globalUserTask']");
		globalUserTask
		.setDocumentation("the adaptions can in principle be programmed to ask for user input, and hence are an adaption for userTask");

		globalUserTask.addAdaption("manualTask");
		globalUserTask.addAdaption("scriptTask");
		globalUserTask.addAdaption("serviceTask");
		globalUserTask.addAdaption("sendTask");
		globalUserTask.addAdaption("globalScriptTask");
		globalUserTask.addAdaption("globalManualTask");
		globalUserTask.addAdaption("userTask");
		add(globalUserTask);
	}

	private void buildLoopTask() {
		AdaptableElement loopTask = new AdaptableElement("loopTask");
		loopTask.setLocatorExpression("//*[(local-name() = 'receiveTask' or local-name() = 'serviceTask' or local-name() = 'manualTask' "
				+ "or local-name() = 'businessRuleTask' or local-name() = 'userTask' or local-name() = 'sendTask'"
				+ "or local-name() = 'scriptTask'  or local-name() = 'globalUserTask' or local-name() = 'globalManualTask' "
				+ "or local-name() = ' globalScriptTask' or local-name() = 'globalBusinessRuleTask') "
				+ "and (child::*[local-name() = 'standardLoopCharacteristics'])]");
		loopTask.addAdaption("exclusiveGatewaysAndSequenceFlows");
		loopTask.addAdaption("complexGatewaysAndSequenceFlows");
		loopTask.addAdaption("loopSubProcess");
		loopTask.addAdaption("adHocSubProcess");
		loopTask.addAdaption("eventSubProcess");
		add(loopTask);
	}

	private void buildSequentialMultiInstanceTask() {
		AdaptableElement multiInstanceTask = new AdaptableElement(
				"sequentialMultiInstanceTask");
		multiInstanceTask
		.setLocatorExpression("//*[(local-name() = 'receiveTask' or local-name() = 'serviceTask' or local-name() = 'manualTask' or local-name() = 'businessRuleTask' or local-name() = 'userTask' or local-name() = 'sendTask' or local-name() = 'scriptTask'  or local-name() = 'globalUserTask' or local-name() = 'globalManualTask' or local-name() = ' globalScriptTask' or local-name() = 'globalBusinessRuleTask') and (child::*[local-name() = 'multiInstanceLoopCharacteristics' and @isSequential='true'])]");

		multiInstanceTask
		.setDocumentation("A sequential multiInstance activity can always be adapted to an ordinary sequential loop");
		multiInstanceTask.addAdaption("exclusiveGatewaysAndSequenceFlows");
		multiInstanceTask.addAdaption("complexGatewaysAndSequenceFlows");
		multiInstanceTask.addAdaption("loopTask");
		multiInstanceTask.addAdaption("loopSubProcess");
		multiInstanceTask.addAdaption("multiInstanceSubProcess");
		multiInstanceTask.addAdaption("adHocSubProcess");
		multiInstanceTask.addAdaption("eventSubProcess");
		add(multiInstanceTask);
	}

	private void buildParallelMultiInstanceTask() {
		AdaptableElement multiInstanceTask = new AdaptableElement(
				"parallelMultiInstanceTask");
		multiInstanceTask
		.setLocatorExpression("//*[(local-name() = 'receiveTask' or local-name() = 'serviceTask' or local-name() = 'manualTask' or local-name() = 'businessRuleTask' or local-name() = 'userTask' or local-name() = 'sendTask' or local-name() = 'scriptTask'  or local-name() = 'globalUserTask' or local-name() = 'globalManualTask' or local-name() = ' globalScriptTask' or local-name() = 'globalBusinessRuleTask') and (child::*[local-name() = 'multiInstanceLoopCharacteristics' and @isSequential='false'])]");

		multiInstanceTask
		.setDocumentation("A parallel multiInstance activity can be be adapted through another construct that supports parallel execution");
		multiInstanceTask.addAdaption("inclusiveGatewaysAndSequenceFlows");
		multiInstanceTask.addAdaption("complexGatewaysAndSequenceFlows");
		multiInstanceTask.addAdaption("multiInstanceSubProcess");
		multiInstanceTask.addAdaption("adHocSubProcess");
		add(multiInstanceTask);
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
		add(task);
	}

}
