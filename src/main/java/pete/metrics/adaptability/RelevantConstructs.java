package pete.metrics.adaptability;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class RelevantConstructs {

	private List<String> constructNames;

	public RelevantConstructs() {
		constructNames = new ArrayList<>();
		constructNames.add("activity");
		constructNames.add("adHocSubProcess");
		constructNames.add("boundaryEvent");
		constructNames.add("businessRuleTask");
		constructNames.add("callActivity");
		constructNames.add("cancelEventDefinition");
		constructNames.add("catchEvent");
		constructNames.add("compensateEventDefinition");
		constructNames.add("complexGateway");
		constructNames.add("conditionalEventDefinition");
		constructNames.add("dataAssociation");
		constructNames.add("dataInput");
		constructNames.add("dataInputAssociation");
		constructNames.add("dataObject");
		constructNames.add("dataOutput");
		constructNames.add("dataOutputAssociation");
		constructNames.add("dataStore");
		constructNames.add("endEvent");
		constructNames.add("error");
		constructNames.add("errorEventDefinition");
		constructNames.add("escalation");
		constructNames.add("escalationEventDefinition");
		constructNames.add("event");
		constructNames.add("eventBasedGateway");
		constructNames.add("extension");
		constructNames.add("extensionElements");
		constructNames.add("formalExpression");
		constructNames.add("gateway");
		constructNames.add("globalBusinessRuleTask");
		constructNames.add("globalManualTask");
		constructNames.add("globalScriptTask");
		constructNames.add("globalUserTask");
		constructNames.add("globalTask");
		constructNames.add("implicitThrowEvent");
		constructNames.add("inclusiveGateway");
		constructNames.add("inputSet");
		constructNames.add("intermediateCatchEvent");
		constructNames.add("intermediateThrowEvent");
		constructNames.add("lane");
		constructNames.add("manualTask");
		constructNames.add("message");
		constructNames.add("messageEventDefinition");
		constructNames.add("messageFlow");
		constructNames.add("messageFlowAssociation");
		constructNames.add("loopCharacteristics");
		constructNames.add("multiInstanceLoopCharacteristics");
		constructNames.add("process");
		constructNames.add("receiveTask");
		constructNames.add("scriptTask");
		constructNames.add("script");
		constructNames.add("sendTask");
		constructNames.add("sequenceFlow");
		constructNames.add("serviceTask");
		constructNames.add("signal");
		constructNames.add("signalEventDefinition");
		constructNames.add("standardLoopCharacteristics");
		constructNames.add("startEvent");
		constructNames.add("subProcess");
		constructNames.add("task");
		constructNames.add("terminateEventDefinition");
		constructNames.add("throwEvent");
		constructNames.add("timerEventDefinition");
		constructNames.add("transaction");
		constructNames.add("userTask");
	}

	public List<String> getRelevantConstructs() {
		return Collections.unmodifiableList(constructNames);
	}

}
