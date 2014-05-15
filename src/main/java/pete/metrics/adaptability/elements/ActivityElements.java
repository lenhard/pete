package pete.metrics.adaptability.elements;

class ActivityElements extends ElementsCollection {

	// Matthias: Manual Task komplett rauswerfen, da keine sinnvolle Adaption.
	// Letztlich kann man alles manuell machen, dann wird aber nichts mehr von
	// der engine verwaltet

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
	}

	private void buildCallActivity() {
		AdaptableElement callActivity = new AdaptableElement("callActivity");
		callActivity.setLocatorExpression("//*[local-name() = 'callActivity']");
		callActivity
				.setDocumentation("A callActivity can be adapted by replacing it with the called globalActivity or process");
		callActivity.addAdaption("embedIntoProcess");

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
				.setDocumentation("EventSubProcesses can be adapted to a different form of subProcess that is executed through a callActivity. "
						+ "In case of an interrupting startEvent, the subProcess can be embedded into the normal flow of control (thus halting the parent process)."
						+ "In case of a noninterrupting startEvent the subProcess must be called in parallel to the normal flow using a parallelGateway.");
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
				.setDocumentation("Due to its unstructured nature, no general advice can be given on how to adapt an adHocSubProcess");

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
		sequentialMultiInstanceSubProcess
				.addAdaption("eventSubProcessAndLoopThatTriggersEvents");
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
				.setDocumentation("Parallel multi-instance subprocesses can be embedded in the process and surrounded by a complexGateway to trigger the same branch multiple times "
						+ "or they can be adapted to an adHocSubprocess");

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
		loopSubProcess.addAdaption("embeddedfragmentWithInclusiveGateways");
		loopSubProcess.addAdaption("embeddedfragmentWithComplexGateways");
		loopSubProcess.addAdaption("eventSubProcessAndLoopThatTriggersEvents");
		loopSubProcess.addAdaption("adHocSubprocess");

		add(loopSubProcess);
	}

	private void buildGlobalBusinessRuleTask() {
		AdaptableElement globalBusinessRuleTask = new AdaptableElement(
				"globalBusinessRuleTask");

		globalBusinessRuleTask
				.setLocatorExpression("//*[local-name() = 'globalBusinessRuleTask']");
		globalBusinessRuleTask
				.setDocumentation("the adaptions can in principle be used to trigger (programmatically or by a user) the execution of a business rule through another program and return the result");
		globalBusinessRuleTask.addAdaption("serviceTask");
		globalBusinessRuleTask.addAdaption("userTask");
		globalBusinessRuleTask.addAdaption("scriptTask");
		globalBusinessRuleTask.addAdaption("businessRuleTask");
		globalBusinessRuleTask.addAdaption("sendAndReceiveTask");
		globalBusinessRuleTask.addAdaption("globalScriptTask");
		globalBusinessRuleTask.addAdaption("globalUserTask");

		add(globalBusinessRuleTask);
	}

	private void buildBusinessRuleTask() {
		AdaptableElement businessRuleTask = new AdaptableElement(
				"businessRuleTask");

		businessRuleTask
				.setDocumentation("the adaptions can in principle be used to trigger (programmatically or by a user) the execution of a business rule through another program and return the result");
		businessRuleTask
				.setLocatorExpression("//*[local-name() = 'businessRuleTask']");
		businessRuleTask.addAdaption("serviceTask");
		businessRuleTask.addAdaption("userTask");
		businessRuleTask.addAdaption("scriptTask");
		businessRuleTask.addAdaption("sendAndReceiveTask");
		businessRuleTask.addAdaption("globalScriptTask");
		businessRuleTask.addAdaption("globalUserTask");
		businessRuleTask.addAdaption("globalBusinessRuleTask");

		add(businessRuleTask);
	}

	private void buildGlobalManualTask() {
		AdaptableElement globalManualTask = new AdaptableElement(
				"globalManualTask");

		// raus gesamt, siehe manual task
		globalManualTask
				.setLocatorExpression("//*[local-name() = 'globalManualTask']");

		globalManualTask
				.setDocumentation("there is alternative general way to represent an arbitrary external action");
		globalManualTask.addAdaption("manualTask");
		add(globalManualTask);
	}

	private void buildManualTask() {
		AdaptableElement manualTask = new AdaptableElement("manualTask");
		manualTask.setLocatorExpression("//*[local-name() = 'manualTask']");
		manualTask
				.setDocumentation("there is alternative general way to represent an arbitrary external action");
		manualTask.addAdaption("globalManualTask");
		add(manualTask);
	}

	private void buildReceiveTask() {
		AdaptableElement receiveTask = new AdaptableElement("receiveTask");
		receiveTask.setLocatorExpression("//*[local-name() = 'receiveTask']");

		receiveTask
				.setDocumentation("the adaptions can in principle be used to wait for a message (programmatically or through a user)");
		receiveTask.addAdaption("serviceTask");
		receiveTask.addAdaption("userTask");
		receiveTask.addAdaption("scriptTask");
		receiveTask.addAdaption("globalScriptTask");
		receiveTask.addAdaption("globalUserTask");
		receiveTask.addAdaption("intermediateMessageEvent");
		receiveTask.addAdaption("eventSubprocessWithMessageStartEvent");

		add(receiveTask);
	}

	private void buildScriptTask() {
		AdaptableElement scriptTask = new AdaptableElement("scriptTask");
		scriptTask.setLocatorExpression("//*[local-name() = 'scriptTask']");

		scriptTask
				.setDocumentation("the adaptions can in principle be used to trigger the execution of a script at some entity. A receiveTask is not suitable as it is passively waiting and a businessRuleTask is too specific");
		scriptTask.addAdaption("serviceTask");
		scriptTask.addAdaption("sendATask");
		scriptTask.addAdaption("userTask");
		scriptTask.addAdaption("globalUserTask");
		scriptTask.addAdaption("globalScriptTask");

		add(scriptTask);
	}

	private void buildGlobalScriptTask() {
		AdaptableElement globalScriptTask = new AdaptableElement(
				"globalScriptTask");
		globalScriptTask
				.setLocatorExpression("//*[local-name() = 'globalScriptTask']");
		// siehe script task
		globalScriptTask
				.setDocumentation("the adaptions can in principle be used to trigger the execution of a script at some entity. "
						+ "A receiveTask is not suitable as it is passively waiting and a businessRuleTask is too specific");
		globalScriptTask.addAdaption("serviceTask");
		globalScriptTask.addAdaption("sendTask");
		globalScriptTask.addAdaption("userTask");
		globalScriptTask.addAdaption("globalScriptTask");
		globalScriptTask.addAdaption("globalUserTask");
		add(globalScriptTask);
	}

	private void buildSendTask() {
		AdaptableElement sendTask = new AdaptableElement("sendTask");
		sendTask.setLocatorExpression("//*[local-name() = 'sendTask']");

		sendTask.setDocumentation("the adaptions can in principle be used to trigger the sending of a message. "
				+ "A receiveTask is not suitable as it is passively waiting and a businessRuleTask is too specific");
		sendTask.addAdaption("serviceTask");
		sendTask.addAdaption("scriptTask");
		sendTask.addAdaption("userTask");
		sendTask.addAdaption("globalScriptTask");
		sendTask.addAdaption("globalUserTask");

		add(sendTask);
	}

	private void buildServiceTask() {
		AdaptableElement serviceTask = new AdaptableElement("serviceTask");
		serviceTask.setLocatorExpression("//*[local-name() = 'serviceTask']");

		serviceTask
				.setDocumentation("the adaptions can in principle be used to trigger service execution, possibly combined with a receive task in case of synchronous communication. "
						+ "A receiveTask is not suitable as it is passively waiting and a businessRuleTask is too specific");
		serviceTask.addAdaption("scriptTask");
		serviceTask.addAdaption("userTask");
		serviceTask.addAdaption("sendTask");
		serviceTask.addAdaption("globalScriptTask");
		serviceTask.addAdaption("globalUserTask");

		add(serviceTask);
	}

	private void buildUserTask() {
		AdaptableElement userTask = new AdaptableElement("userTask");
		userTask.setLocatorExpression("//*[local-name() = 'userTask']");
		userTask.setDocumentation("the adaptions can in principle be programmed to ask for user input, and hence are an adaption for userTask");
		userTask.addAdaption("scriptTask");
		userTask.addAdaption("serviceTask");
		userTask.addAdaption("sendAndReceiveTask");
		userTask.addAdaption("globalScriptTask");
		userTask.addAdaption("globalUserTask");

		add(userTask);
	}

	private void buildGlobalUserTask() {
		AdaptableElement globalUserTask = new AdaptableElement("globalUserTask");
		globalUserTask
				.setLocatorExpression("/*[local-name() = 'globalUserTask']");
		globalUserTask
				.setDocumentation("the adaptions can in principle be programmed to ask for user input, and hence are an adaption for userTask");
		globalUserTask.addAdaption("scriptTask");
		globalUserTask.addAdaption("serviceTask");
		globalUserTask.addAdaption("sendTask");
		globalUserTask.addAdaption("globalScriptTask");
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
		loopTask.setDocumentation("A loopTask can be adapted to a subProcess or by a combination of the task with certian gateways");
		loopTask.addAdaption("exclusiveGatewaysAndSequenceFlows");
		loopTask.addAdaption("inclusiveGatewaysAndSequenceFlows");
		loopTask.addAdaption("complexGatewaysAndSequenceFlows");
		loopTask.addAdaption("loopSubProcess");
		loopTask.addAdaption("adHocSubProcess");

		add(loopTask);
	}

	private void buildSequentialMultiInstanceTask() {
		AdaptableElement multiInstanceTask = new AdaptableElement(
				"sequentialMultiInstanceTask");
		multiInstanceTask
				.setLocatorExpression("//*[(local-name() = 'receiveTask' or local-name() = 'serviceTask' or local-name() = 'manualTask' or local-name() = 'businessRuleTask' or local-name() = 'userTask' or local-name() = 'sendTask' or local-name() = 'scriptTask'  or local-name() = 'globalUserTask' or local-name() = 'globalManualTask' or local-name() = ' globalScriptTask' or local-name() = 'globalBusinessRuleTask') and (child::*[local-name() = 'multiInstanceLoopCharacteristics' and @isSequential='true'])]");

		multiInstanceTask
				.setDocumentation("A sequential multiInstance activity can be adapted to an ordinary sequential loop or a different represenation thereof, as well as to different subProcesses.");
		multiInstanceTask.addAdaption("exclusiveGatewaysAndSequenceFlows");
		multiInstanceTask.addAdaption("complexGatewaysAndSequenceFlows");
		multiInstanceTask.addAdaption("loopTask");
		multiInstanceTask.addAdaption("loopSubProcess");
		multiInstanceTask.addAdaption("multiInstanceSubProcess");
		multiInstanceTask.addAdaption("adHocSubProcess");

		add(multiInstanceTask);
	}

	private void buildParallelMultiInstanceTask() {
		AdaptableElement multiInstanceTask = new AdaptableElement(
				"parallelMultiInstanceTask");
		multiInstanceTask
				.setLocatorExpression("//*[(local-name() = 'receiveTask' or local-name() = 'serviceTask' or local-name() = 'manualTask' or local-name() = 'businessRuleTask' or local-name() = 'userTask' or local-name() = 'sendTask' or local-name() = 'scriptTask'  or local-name() = 'globalUserTask' or local-name() = 'globalManualTask' or local-name() = ' globalScriptTask' or local-name() = 'globalBusinessRuleTask') and (child::*[local-name() = 'multiInstanceLoopCharacteristics' and @isSequential='false'])]");

		multiInstanceTask
				.setDocumentation("A parallel multiInstance activity can be be adapted to subProcess that allow for the execution of multiInstances in parallel");

		multiInstanceTask.addAdaption("multiInstanceSubProcess");
		multiInstanceTask.addAdaption("adHocSubProcess");
		add(multiInstanceTask);
	}

}
