package pete.metrics.adaptability;

import java.util.ArrayList;
import java.util.List;

class RelevantElements {

	private List<AdaptableElement> elements;

	public RelevantElements() {
		elements = new ArrayList<>();
		elements.add(new AdaptableElement("activity"));
		elements.add(new AdaptableElement("adHocSubProcess"));
		elements.add(new AdaptableElement("boundaryEvent"));
		elements.add(new AdaptableElement("businessRuleTask"));
		elements.add(new AdaptableElement("callActivity"));
		elements.add(new AdaptableElement("cancelEventDefinition"));
		elements.add(new AdaptableElement("catchEvent"));
		elements.add(new AdaptableElement("compensateEventDefinition"));
		elements.add(new AdaptableElement("complexGateway"));
		elements.add(new AdaptableElement("conditionalEventDefinition"));
		elements.add(new AdaptableElement("dataAssociation"));
		elements.add(new AdaptableElement("dataInput"));
		elements.add(new AdaptableElement("dataInputAssociation"));
		elements.add(new AdaptableElement("dataObject"));
		elements.add(new AdaptableElement("dataOutput"));
		elements.add(new AdaptableElement("dataOutputAssociation"));
		elements.add(new AdaptableElement("dataStore"));
		elements.add(new AdaptableElement("endEvent"));
		elements.add(new AdaptableElement("error"));
		elements.add(new AdaptableElement("errorEventDefinition"));
		elements.add(new AdaptableElement("escalation"));
		elements.add(new AdaptableElement("escalationEventDefinition"));
		elements.add(new AdaptableElement("event"));
		elements.add(new AdaptableElement("eventBasedGateway"));
		elements.add(new AdaptableElement("extension"));
		elements.add(new AdaptableElement("extensionElements"));
		elements.add(new AdaptableElement("formalExpression"));
		elements.add(new AdaptableElement("gateway"));
		elements.add(new AdaptableElement("globalBusinessRuleTask"));
		elements.add(new AdaptableElement("globalManualTask"));
		elements.add(new AdaptableElement("globalScriptTask"));
		elements.add(new AdaptableElement("globalUserTask"));
		elements.add(new AdaptableElement("globalTask"));
		elements.add(new AdaptableElement("implicitThrowEvent"));
		elements.add(new AdaptableElement("inclusiveGateway"));
		elements.add(new AdaptableElement("inputSet"));
		elements.add(new AdaptableElement("intermediateCatchEvent"));
		elements.add(new AdaptableElement("intermediateThrowEvent"));
		elements.add(new AdaptableElement("lane"));
		elements.add(new AdaptableElement("manualTask"));
		elements.add(new AdaptableElement("message"));
		elements.add(new AdaptableElement("messageEventDefinition"));
		elements.add(new AdaptableElement("messageFlow"));
		elements.add(new AdaptableElement("messageFlowAssociation"));
		elements.add(new AdaptableElement("loopCharacteristics"));
		elements.add(new AdaptableElement("multiInstanceLoopCharacteristics"));
		elements.add(new AdaptableElement("process"));
		elements.add(new AdaptableElement("receiveTask"));
		elements.add(new AdaptableElement("scriptTask"));
		elements.add(new AdaptableElement("script"));
		elements.add(new AdaptableElement("sendTask"));
		elements.add(new AdaptableElement("sequenceFlow"));
		elements.add(new AdaptableElement("serviceTask"));
		elements.add(new AdaptableElement("signal"));
		elements.add(new AdaptableElement("signalEventDefinition"));
		elements.add(new AdaptableElement("standardLoopCharacteristics"));
		elements.add(new AdaptableElement("startEvent"));
		elements.add(new AdaptableElement("subProcess"));
		elements.add(new AdaptableElement("task"));
		elements.add(new AdaptableElement("terminateEventDefinition"));
		elements.add(new AdaptableElement("throwEvent"));
		elements.add(new AdaptableElement("timerEventDefinition"));
		elements.add(new AdaptableElement("transaction"));
		elements.add(new AdaptableElement("userTask"));
	}

	public List<String> getRelevantElements() {
		List<String> result = new ArrayList<>(elements.size());
		for (AdaptableElement element : elements) {
			result.add(element.getName());
		}
		return result;
	}

}
