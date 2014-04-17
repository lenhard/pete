package pete.metrics.adaptability.elements;


class EventElements extends ElementsCollection {

	public EventElements() {
		buildTopLevelStartEvents();
		buildEventSubProcessStartEvents();
		buildEndEvents();
		buildIntermediateEvents();
		buildBoundaryEvents();
	}

	private void buildTopLevelStartEvents() {
		buildNoneStartEvent();
		buildMessageStartEvent();
		buildTimerStartEvent();
		buildConditionalStartEvent();
		buildSignalStartEvent();
		buildMultipleStartEvent();
		buildMultipleParallelStartEvent();
		buildSubProcessStartEvent();
	}

	private void buildEventSubProcessStartEvents() {
		buildEventSubProcessNonInterruptingMessageStartEvent();
		buildEventSubProcessInterruptingMessageStartEvent();
		buildEventSubProcessInterruptingTimerStartEvent();
		buildEventSubProcessNonInterruptingTimerStartEvent();
		buildEventSubProcessInterruptingEscalationStartEvent();
		buildEventSubProcessNonInterruptingEscalationStartEvent();
		buildEventSubProcessErrorStartEvent();
		buildEventSubProcessCompensationStartEvent();
		buildEventSubProcessNonInterruptingConditionalStartEvent();
		buildEventSubProcessInterruptingConditionalStartEvent();
		buildEventSubProcessNonInterruptingSignalStartEvent();
		buildEventSubProcessInterruptingSignalStartEvent();
		buildEventSubProcessNonInterruptingMultipleStartEvent();
		buildEventSubProcessInterruptingMultipleStartEvent();
		buildEventSubProcessNonInterruptingParallelMultipleStartEvent();
		buildEventSubProcessInterruptingParallelMultipleStartEvent();
	}

	private void buildEndEvents() {
		buildNoneEndEvent();
		buildMessageEndEvent();
		buildErrorEndEvent();
		buildEscalationEndEvent();
		buildCancelEndEvent();
		buildCompensationEndEvent();
		buildSignalEndEvent();
		buildTerminateEndEvent();
		buildMultipleEndEvent();
	}

	private void buildIntermediateEvents() {
		buildIntermediateNoneThrowEvent();
		buildIntermediateMessageThrowEvent();
		buildIntermediateMessageCatchEvent();
		buildIntermediateTimerCatchEvent();
		buildIntermediateEscalationThrowEvent();
		buildIntermediateCompensationThrowEvent();
		buildIntermediateConditionalCatchEvent();
		buildIntermediateSignalThrowEvent();
		buildIntermediateSignalCatchEvent();
		buildIntermediateMultipleThrowEvent();
		buildIntermediateMultipleCatchEvent();
		buildIntermediateMultipleParallelCatchEvent();
	}

	private void buildBoundaryEvents(){
		buildInterruptingMessageBoundaryEvent();
		buildErrorBoundaryEvent();
		buildNonInterruptingMessageBoundaryEvent();
		buildNonInterruptingTimerBoundaryEvent();
		buildInterruptingTimerBoundaryEvent();
	}

	public String buildEventXPathExpression(String event, String eventType) {
		return "//*[local-name() = '"
				+ event
				+ "Event' and (child::*[local-name() = '"
				+ eventType
				+ "EventDefinition'] or child::*[local-name() = 'eventDefinitionRef' and text() = //*[local-name() = '"
				+ eventType
				+ "EventDefinition']/@id]) and (count(child::*[contains(local-name(),'ventDefinition')]) = 1)]";
	}

	private void buildInterruptingMessageBoundaryEvent() {
		AdaptableElement interruptingMessageBoundaryEvent = new AdaptableElement(
				"interruptingMessageBoundaryEvent");
		interruptingMessageBoundaryEvent
		.setLocatorExpression(buildInterruptingBoundaryEventXPathExpression("message"));
		interruptingMessageBoundaryEvent
		.setDocumentation("An interrupting boundary message event can be adapted to another interrupting boundary event that uses an active trigger");
		interruptingMessageBoundaryEvent.addAdaption("interruptingEscalationBoundaryEvent");
		interruptingMessageBoundaryEvent.addAdaption("interruptingErrorBoundaryEvent");
		interruptingMessageBoundaryEvent.addAdaption("interruptingSignalBoundaryEvent");
		interruptingMessageBoundaryEvent.addAdaption("interruptingConditionalBoundaryEvent");
		interruptingMessageBoundaryEvent.addAdaption("interruptingMultipleBoundaryEvent");
		interruptingMessageBoundaryEvent.addAdaption("interruptingMultipleParallelBoundaryEvent");
		add(interruptingMessageBoundaryEvent);
	}

	private void buildNonInterruptingMessageBoundaryEvent() {
		AdaptableElement nonInterruptingMessageBoundaryEvent = new AdaptableElement(
				"nonInterruptingMessageBoundaryEvent");
		nonInterruptingMessageBoundaryEvent
		.setLocatorExpression(buildNonInterruptingBoundaryEventXPathExpression("message"));
		nonInterruptingMessageBoundaryEvent
		.setDocumentation("An non-interrupting boundary message event can be adapted to another non-interrupting boundary event that uses an active trigger");
		nonInterruptingMessageBoundaryEvent.addAdaption("nonInterruptingEscalationBoundaryEvent");
		nonInterruptingMessageBoundaryEvent.addAdaption("nonInterruptingSignalBoundaryEvent");
		nonInterruptingMessageBoundaryEvent.addAdaption("nonInterruptingConditionalBoundaryEvent");
		nonInterruptingMessageBoundaryEvent.addAdaption("nonInterruptingMultipleBoundaryEvent");
		nonInterruptingMessageBoundaryEvent.addAdaption("nonInterruptingMultipleParallelBoundaryEvent");
		add(nonInterruptingMessageBoundaryEvent);
	}

	private void buildNonInterruptingTimerBoundaryEvent() {
		AdaptableElement nonInterruptingTimerBoundaryEvent = new AdaptableElement(
				"nonInterruptingTimerBoundaryEvent");
		nonInterruptingTimerBoundaryEvent
		.setLocatorExpression(buildNonInterruptingBoundaryEventXPathExpression("timer"));
		nonInterruptingTimerBoundaryEvent
		.addAdaption("A nonInterruptingTimerBoundaryEvent can be adapted to another noninterruptingBoundaryEvent that is triggered in some fashion, as it is possible to calculate the expiration of the time and trigger the event when it does");
		nonInterruptingTimerBoundaryEvent.addAdaption("noninterruptingSignalBoundaryEvent");
		nonInterruptingTimerBoundaryEvent.addAdaption("noninterruptingEscalationBoundaryEvent");
		nonInterruptingTimerBoundaryEvent.addAdaption("noninterruptingConditionalBoundaryEvent");
		nonInterruptingTimerBoundaryEvent.addAdaption("noninterruptingMessageBoundaryEvent");
		nonInterruptingTimerBoundaryEvent.addAdaption("noninterruptingSignalBoundaryEvent");
		nonInterruptingTimerBoundaryEvent.addAdaption("noninterruptingMultipleBoundaryEvent");
		nonInterruptingTimerBoundaryEvent.addAdaption("noninterruptingParallelMultipleBoundaryEvent");
		add(nonInterruptingTimerBoundaryEvent);
	}

	private void buildInterruptingTimerBoundaryEvent() {
		AdaptableElement interruptingTimerBoundaryEvent = new AdaptableElement(
				"interruptingTimerBoundaryEvent");
		interruptingTimerBoundaryEvent
		.setLocatorExpression(buildInterruptingBoundaryEventXPathExpression("timer"));
		interruptingTimerBoundaryEvent
		.addAdaption("An interruptingTimerBoundaryEvent can be adapted to another interruptingBoundaryEvent that is triggered in some fashion, as it is possible to calculate the expiration of the time and trigger the event when it does");
		interruptingTimerBoundaryEvent.addAdaption("noninterruptingSignalBoundaryEvent");
		interruptingTimerBoundaryEvent.addAdaption("noninterruptingEscalationBoundaryEvent");
		interruptingTimerBoundaryEvent.addAdaption("noninterruptingConditionalBoundaryEvent");
		interruptingTimerBoundaryEvent.addAdaption("noninterruptingMessageBoundaryEvent");
		interruptingTimerBoundaryEvent.addAdaption("noninterruptingSignalBoundaryEvent");
		interruptingTimerBoundaryEvent.addAdaption("noninterruptingMultipleBoundaryEvent");
		interruptingTimerBoundaryEvent.addAdaption("noninterruptingParallelMultipleBoundaryEvent");
		add(interruptingTimerBoundaryEvent);
	}

	private void buildIntermediateNoneThrowEvent() {
		AdaptableElement noneThrowEvent = new AdaptableElement(
				"intermediateNoneThrowEvent");
		noneThrowEvent.setLocatorExpression("//*[local-name() = 'intermediateThrowEvent' and not(child::*[contains(local-name(),'ventDefinition')])]");
		noneThrowEvent.setDocumentation("This event can be adapted to another intermediateThrowEvent used in normal flow");
		noneThrowEvent.addAdaption("intermediateMessageThrowEvent");
		noneThrowEvent.addAdaption("intermediateSignalThrowEvent");
		noneThrowEvent.addAdaption("intermediateEscalationThrowEvent");
		noneThrowEvent.addAdaption("intermediateMultipleThrowEvent");
		noneThrowEvent.addAdaption("intermediateParallelMultipleThrowEvent");
		add(noneThrowEvent);
	}

	private void buildIntermediateMessageThrowEvent() {
		AdaptableElement messageThrowEvent = new AdaptableElement(
				"intermediateMessageThrowEvent");
		messageThrowEvent.setLocatorExpression(buildIntermediateThrowEventXPathExpression("message"));
		System.out.println(messageThrowEvent.getLocatorExpression());
		messageThrowEvent.setDocumentation("This event can be adapted to another intermediateThrowEvent used in normal flow that provides a trigger");
		messageThrowEvent.addAdaption("intermediateSignalThrowEvent");
		messageThrowEvent.addAdaption("intermediateMultipleThrowEvent");
		messageThrowEvent.addAdaption("intermediateParallelMultipleThrowEvent");
		add(messageThrowEvent);
	}

	private void buildIntermediateMessageCatchEvent() {
		AdaptableElement messageCatchEvent = new AdaptableElement(
				"intermediateMessageCatchEvent");
		messageCatchEvent.setLocatorExpression(buildIntermediateCatchEventXPathExpression("message"));
		System.out.println(messageCatchEvent.getLocatorExpression());
		messageCatchEvent.setDocumentation("This event can be adapted to another intermediateCatchEvent used in normal flow that consumes a trigger");
		messageCatchEvent.addAdaption("intermediateSignalCatchEvent");
		messageCatchEvent.addAdaption("intermediateMultipleCatchEvent");
		messageCatchEvent.addAdaption("intermediateParallelMultipleCatchEvent");
		add(messageCatchEvent);
	}

	private void buildIntermediateTimerCatchEvent() {
		AdaptableElement timerCatchEvent = new AdaptableElement(
				"intermediateTimerCatchEvent");
		timerCatchEvent
		.setLocatorExpression(buildIntermediateCatchEventXPathExpression("timer"));
		timerCatchEvent
		.addAdaption("A timerStartEvent can be adapted to another catchEvent that is triggered in some fashion, as it is possible to calculate the expiration of the time and trigger the event when it does");
		timerCatchEvent.addAdaption("intermediateConditionalCatchEvent");
		timerCatchEvent.addAdaption("intermediateMessageCatchEvent");
		timerCatchEvent.addAdaption("intermediateSignalCatchEvent");
		timerCatchEvent.addAdaption("intermediateMultipleCatchEvent");
		timerCatchEvent.addAdaption("intermediateParallelMultipleCatchEvent");
		add(timerCatchEvent);
	}

	private void buildIntermediateEscalationThrowEvent() {
		AdaptableElement escalationThrowEvent = new AdaptableElement(
				"intermediateEscalationThrowEvent");
		escalationThrowEvent
		.setLocatorExpression(buildIntermediateThrowEventXPathExpression("escalation"));
		escalationThrowEvent
		.setDocumentation("A intermediate escalation event can be adapted to another intermediate event that uses an active trigger");
		escalationThrowEvent.addAdaption("intermediateMessageThrowEvent");
		escalationThrowEvent.addAdaption("intermediateSignalThrowEvent");
		escalationThrowEvent.addAdaption("intermediateMultipleThrowEvent");
		escalationThrowEvent
		.addAdaption("intermediateMultipleParallelThrowEvent");
		add(escalationThrowEvent);
	}

	private void buildIntermediateCompensationThrowEvent() {
		AdaptableElement compensationThrowEvent = new AdaptableElement(
				"intermediateCompensationThrowEvent");
		compensationThrowEvent
		.setLocatorExpression(buildIntermediateThrowEventXPathExpression("compensation"));
		compensationThrowEvent
		.setDocumentation("A intermediate compensation event cannot be adapted since there is no other intermediate throwing event with compensation semantics");
		add(compensationThrowEvent);
	}

	private void buildIntermediateConditionalCatchEvent() {
		AdaptableElement conditionalCatchEvent = new AdaptableElement(
				"intermediateConditionalCatchEvent");
		conditionalCatchEvent
		.setLocatorExpression(buildIntermediateCatchEventXPathExpression("conditional"));
		conditionalCatchEvent
		.setDocumentation("An intermediate conditional catch event can be adapted to another intermediate catch event that uses an active trigger");
		conditionalCatchEvent.addAdaption("intermediateSignalCatchEvent");
		conditionalCatchEvent.addAdaption("intermediateMessageCatchEvent");
		conditionalCatchEvent.addAdaption("intermediateMultipleCatchEvent");
		conditionalCatchEvent.addAdaption("intermediateMultipleParallelCatchEvent");
		add(conditionalCatchEvent);
	}

	private void buildIntermediateSignalThrowEvent() {
		AdaptableElement signalThrowEvent = new AdaptableElement(
				"intermediateSignalThrowEvent");
		signalThrowEvent
		.setLocatorExpression(buildIntermediateThrowEventXPathExpression("signal"));
		signalThrowEvent
		.addAdaption("A intermediateSignalThrowEvent can be adapted to another intermediateThrowEvent that is triggered in some fashion");
		signalThrowEvent.addAdaption("intermediateMessageThrowEvent");
		signalThrowEvent.addAdaption("intermediateMultipleThrowEvent");
		signalThrowEvent.addAdaption("intermediateParallelMultipleThrowEvent");
		add(signalThrowEvent);
	}

	private void buildIntermediateSignalCatchEvent() {
		AdaptableElement signalCatchEvent = new AdaptableElement(
				"intermediateSignalCatchEvent");
		signalCatchEvent
		.setLocatorExpression(buildIntermediateThrowEventXPathExpression("catch"));
		signalCatchEvent
		.addAdaption("A intermediateSignalCatchEvent can be adapted to another intermediateCatchEvent that receives a trigger");
		signalCatchEvent.addAdaption("intermediateMessageCatchEvent");
		signalCatchEvent.addAdaption("intermediateConditionalCatchEvent");
		signalCatchEvent.addAdaption("intermediateMultipleCatchEvent");
		signalCatchEvent.addAdaption("intermediateParallelMultipleCatchEvent");
		add(signalCatchEvent);
	}

	private void buildIntermediateMultipleThrowEvent() {
		AdaptableElement multipleThrowEvent = new AdaptableElement(
				"intermediateMultipleThrowEvent");
		multipleThrowEvent
		.setLocatorExpression("//*[local-name() = 'intermediateThrowEvent' and not(@parallelMultiple = 'true') and (count(child::*[contains(local-name(),'ventDefinition')]) > 1)]");
		multipleThrowEvent
		.addAdaption("An intermediateMultipleThrowEvent can be reduced to  the available alternative throw events surrounded by an exclusiveGateway");
		multipleThrowEvent.addAdaption("intermediateThrowEventsAndExclusiveGateway");
		add(multipleThrowEvent);
	}

	private void buildIntermediateMultipleCatchEvent() {
		AdaptableElement multipleCatchEvent = new AdaptableElement(
				"intermediateMultipleCatchEvent");
		multipleCatchEvent
		.setLocatorExpression("//*[local-name() = 'intermediateCatchEvent' and not(@parallelMultiple = 'true') and (count(child::*[contains(local-name(),'ventDefinition')]) > 1)]");
		multipleCatchEvent
		.addAdaption("An intermediateMultipleCatchEvent can be reduced to the available alternative catch events surrounded by an exclusiveGateway");
		multipleCatchEvent.addAdaption("intermediateCatchEventsAndExclusiveGateway");
		add(multipleCatchEvent);
	}

	private void buildIntermediateMultipleParallelCatchEvent() {
		AdaptableElement multipleParallelCatchEvent = new AdaptableElement(
				"intermediateMultipleParallelCatchEvent");
		multipleParallelCatchEvent
		.setLocatorExpression("//*[local-name() = 'intermediateCatchEvent' and (@parallelMultiple = 'true') and (count(child::*[contains(local-name(),'ventDefinition')]) > 1)]");
		multipleParallelCatchEvent
		.addAdaption("An intermediateMultipleParallelCatchEvent can be reduced to the available alternative catch events surrounded by an gateway that triggers multiple branches");
		multipleParallelCatchEvent.addAdaption("intermediateCatchEventsAndParallelGateway");
		multipleParallelCatchEvent.addAdaption("intermediateCatchEventsAndInclusiveGateway");
		multipleParallelCatchEvent.addAdaption("intermediateCatchEventsAndComplexGateway");
		add(multipleParallelCatchEvent);
	}

	private void buildEventSubProcessNonInterruptingMessageStartEvent() {
		AdaptableElement nonInterruptingMessageStartEvent = new AdaptableElement(
				"nonInterruptingMessageStartEvent");
		nonInterruptingMessageStartEvent
		.setLocatorExpression(buildEventSubProcessNonInterruptingStartEventXPathExpression("message"));
		nonInterruptingMessageStartEvent
		.setDocumentation("A non-interrupting message start event can be adapted to another non-interrupting start event that uses an active trigger");
		nonInterruptingMessageStartEvent.addAdaption("escalationStartEvent");
		nonInterruptingMessageStartEvent.addAdaption("signalStartEvent");
		nonInterruptingMessageStartEvent.addAdaption("conditionalStartEvent");
		nonInterruptingMessageStartEvent.addAdaption("multipleStartEvent");
		nonInterruptingMessageStartEvent
		.addAdaption("multipleParallelStartEvent");
		add(nonInterruptingMessageStartEvent);
	}



	private void buildEventSubProcessInterruptingMessageStartEvent() {
		AdaptableElement interruptingMessageStartEvent = new AdaptableElement(
				"interruptingMessageStartEvent");
		interruptingMessageStartEvent
		.setLocatorExpression(buildEventSubProcessInterruptingStartEventXPathExpression("message"));
		interruptingMessageStartEvent
		.setDocumentation("An interrupting message start event can be adapted to another interrupting start event that uses an active trigger");
		interruptingMessageStartEvent.addAdaption("escalationStartEvent");
		interruptingMessageStartEvent.addAdaption("errorStartEvent");
		interruptingMessageStartEvent.addAdaption("signalStartEvent");
		interruptingMessageStartEvent.addAdaption("conditionalStartEvent");
		interruptingMessageStartEvent.addAdaption("multipleStartEvent");
		interruptingMessageStartEvent.addAdaption("multipleParallelStartEvent");
		add(interruptingMessageStartEvent);
	}

	private void buildEventSubProcessNonInterruptingSignalStartEvent() {
		AdaptableElement signalStartEvent = new AdaptableElement(
				"nonInterruptingSignalStartEvent");
		signalStartEvent
		.setLocatorExpression(buildEventSubProcessNonInterruptingStartEventXPathExpression("signal"));
		signalStartEvent
		.setDocumentation("A non-interrupting signal start event can be adapted to another non-interrupting start event that uses an active trigger");
		signalStartEvent.addAdaption("escalationStartEvent");
		signalStartEvent.addAdaption("messageStartEvent");
		signalStartEvent.addAdaption("conditionalStartEvent");
		signalStartEvent.addAdaption("multipleStartEvent");
		signalStartEvent
		.addAdaption("multipleParallelStartEvent");
		add(signalStartEvent);
	}

	private void buildEventSubProcessInterruptingSignalStartEvent() {
		AdaptableElement signalStartEvent = new AdaptableElement(
				"interruptingSignalStartEvent");
		signalStartEvent
		.setLocatorExpression(buildEventSubProcessInterruptingStartEventXPathExpression("signal"));
		signalStartEvent
		.setDocumentation("An interrupting signal start event can be adapted to another interrupting start event that uses an active trigger");
		signalStartEvent.addAdaption("escalationStartEvent");
		signalStartEvent.addAdaption("errorStartEvent");
		signalStartEvent.addAdaption("messageStartEvent");
		signalStartEvent.addAdaption("conditionalStartEvent");
		signalStartEvent.addAdaption("multipleStartEvent");
		signalStartEvent.addAdaption("multipleParallelStartEvent");
		add(signalStartEvent);
	}

	private void buildEventSubProcessNonInterruptingConditionalStartEvent() {
		AdaptableElement conditionalStartEvent = new AdaptableElement(
				"nonInterruptingConditionalStartEvent");
		conditionalStartEvent
		.setLocatorExpression(buildEventSubProcessNonInterruptingStartEventXPathExpression("conditional"));
		conditionalStartEvent
		.setDocumentation("A non-interrupting conditional start event can be adapted to another non-interrupting start event that uses an active trigger");
		conditionalStartEvent.addAdaption("signalStartEvent");
		conditionalStartEvent.addAdaption("messageStartEvent");
		conditionalStartEvent.addAdaption("multipleStartEvent");
		conditionalStartEvent
		.addAdaption("multipleParallelStartEvent");
		add(conditionalStartEvent);
	}

	private void buildEventSubProcessInterruptingConditionalStartEvent() {
		AdaptableElement conditionalStartEvent = new AdaptableElement(
				"interruptingConditionalStartEvent");
		conditionalStartEvent
		.setLocatorExpression(buildEventSubProcessInterruptingStartEventXPathExpression("conditional"));
		conditionalStartEvent
		.setDocumentation("An interrupting conditional start event can be adapted to another interrupting start event that uses an active trigger");
		conditionalStartEvent.addAdaption("escalationStartEvent");
		conditionalStartEvent.addAdaption("errorStartEvent");
		conditionalStartEvent.addAdaption("signalStartEvent");
		conditionalStartEvent.addAdaption("messageStartEvent");
		conditionalStartEvent.addAdaption("multipleStartEvent");
		conditionalStartEvent.addAdaption("multipleParallelStartEvent");
		add(conditionalStartEvent);
	}

	private void buildEventSubProcessErrorStartEvent(){
		AdaptableElement errorStartEvent = new AdaptableElement("interruptingErrorStartEvent");
		errorStartEvent.setLocatorExpression(buildEventSubProcessInterruptingStartEventXPathExpression("error"));
		errorStartEvent.setDocumentation("An interrupting error start event can be adapted to another interrupting start event that uses an active trigger");
		errorStartEvent.addAdaption("escalationStartEvent");
		errorStartEvent.addAdaption("messageStartEvent");
		errorStartEvent.addAdaption("signalStartEvent");
		errorStartEvent.addAdaption("conditionalStartEvent");
		errorStartEvent.addAdaption("multipleStartEvent");
		errorStartEvent.addAdaption("multipleParallelStartEvent");
		add(errorStartEvent);
	}

	private void buildEventSubProcessCompensationStartEvent(){
		AdaptableElement compensationStartEvent = new AdaptableElement("compensationStartEvent");
		compensationStartEvent.setLocatorExpression(buildEventSubProcessInterruptingStartEventXPathExpression("compensation"));
		compensationStartEvent.setDocumentation("A compensation start event cannot be adapted since there is no alternative mechanism to trigger the compensation of a process that has completed");
		add(compensationStartEvent);
	}

	private void buildEventSubProcessNonInterruptingEscalationStartEvent() {
		AdaptableElement escalationStartEvent = new AdaptableElement(
				"nonInterruptingEscalationStartEvent");
		escalationStartEvent
		.setLocatorExpression(buildEventSubProcessNonInterruptingStartEventXPathExpression("escalation"));
		escalationStartEvent
		.setDocumentation("A non-interrupting escalation start event can be adapted to another non-interrupting start event that uses an active trigger");
		escalationStartEvent.addAdaption("messageStartEvent");
		escalationStartEvent.addAdaption("signalStartEvent");
		escalationStartEvent.addAdaption("conditionalStartEvent");
		escalationStartEvent.addAdaption("multipleStartEvent");
		escalationStartEvent
		.addAdaption("multipleParallelStartEvent");
		add(escalationStartEvent);
	}

	private void buildEventSubProcessInterruptingEscalationStartEvent() {
		AdaptableElement interruptingEscalationStartEvent = new AdaptableElement(
				"interruptingEscalationStartEvent");
		interruptingEscalationStartEvent
		.setLocatorExpression(buildEventSubProcessInterruptingStartEventXPathExpression("escalation"));
		interruptingEscalationStartEvent
		.setDocumentation("An interrupting escalation start event can be adapted to another interrupting start event that uses an active trigger");
		interruptingEscalationStartEvent.addAdaption("messageStartEvent");
		interruptingEscalationStartEvent.addAdaption("errorStartEvent");
		interruptingEscalationStartEvent.addAdaption("signalStartEvent");
		interruptingEscalationStartEvent.addAdaption("conditionalStartEvent");
		interruptingEscalationStartEvent.addAdaption("multipleStartEvent");
		interruptingEscalationStartEvent.addAdaption("multipleParallelStartEvent");
		add(interruptingEscalationStartEvent);
	}


	private void buildEventSubProcessNonInterruptingTimerStartEvent() {
		AdaptableElement timerStartEvent = new AdaptableElement(
				"nonInterruptingTimerStartEvent");
		timerStartEvent
		.setLocatorExpression(buildEventSubProcessNonInterruptingStartEventXPathExpression("timer"));
		timerStartEvent
		.addAdaption("A timerStartEvent can be adapted to another startEvent that is triggered in some fashion, as it is possible to calculate the expiration of the time and trigger the event when it does");
		timerStartEvent.addAdaption("signalStartEvent");
		timerStartEvent.addAdaption("escalationStartEvent");
		timerStartEvent.addAdaption("messageStartEvent");
		timerStartEvent.addAdaption("signalStartEvent");
		timerStartEvent.addAdaption("multipleStartEvent");
		timerStartEvent.addAdaption("parallelMultipleStartEvent");
		add(timerStartEvent);
	}

	private void buildEventSubProcessInterruptingTimerStartEvent() {
		AdaptableElement timerStartEvent = new AdaptableElement(
				"interruptingTimerStartEvent");
		timerStartEvent
		.setLocatorExpression(buildEventSubProcessInterruptingStartEventXPathExpression("timer"));
		timerStartEvent
		.addAdaption("A timerStartEvent can be adapted to another startEvent that is triggered in some fashion, as it is possible to calculate the expiration of the time and trigger the event when it does");
		timerStartEvent.addAdaption("messageStartEvent");
		timerStartEvent.addAdaption("escalationStartEvent");
		timerStartEvent.addAdaption("errorStartEvent");
		timerStartEvent.addAdaption("signalStartEvent");
		timerStartEvent.addAdaption("conditionalStartEvent");
		timerStartEvent.addAdaption("multipleStartEvent");
		timerStartEvent.addAdaption("multipleParallelStartEvent");
		add(timerStartEvent);
	}

	private void buildEventSubProcessInterruptingMultipleStartEvent() {
		AdaptableElement multipleStartEvent = new AdaptableElement(
				"interruptingMultipleStartEvent");
		multipleStartEvent
		.setLocatorExpression("//*[local-name() = 'subProcess' and @triggeredByEvent = 'true']/*[local-name() = 'startEvent' and (@isInterrupting = 'true') and not(@parallelMultiple = 'true') and (count(child::*[contains(local-name(),'ventDefinition')]) > 1)]");
		multipleStartEvent
		.addAdaption("A interrupting multipleStartEvent can be reduced to one of the available alternative interrupting start events");
		multipleStartEvent.addAdaption("messageStartEvent");
		multipleStartEvent.addAdaption("conditionalStartEvent");
		multipleStartEvent.addAdaption("signalStartEvent");
		multipleStartEvent.addAdaption("timerStartEvent");
		multipleStartEvent.addAdaption("errorStartEvent");
		multipleStartEvent.addAdaption("escalationStartEvent");
		add(multipleStartEvent);
	}

	private void buildEventSubProcessNonInterruptingMultipleStartEvent() {
		AdaptableElement multipleStartEvent = new AdaptableElement(
				"nonInterruptingMultipleStartEvent");
		multipleStartEvent
		.setLocatorExpression("//*[local-name() = 'subProcess' and @triggeredByEvent = 'true']/*[local-name() = 'startEvent' and not (@isInterrupting = 'true') and not(@parallelMultiple = 'true') and (count(child::*[contains(local-name(),'ventDefinition')]) > 1)]");
		multipleStartEvent
		.addAdaption("A non-interrupting multipleStartEvent can be reduced to one of the available alternative non-interrupting start events");
		multipleStartEvent.addAdaption("messageStartEvent");
		multipleStartEvent.addAdaption("conditionalStartEvent");
		multipleStartEvent.addAdaption("signalStartEvent");
		multipleStartEvent.addAdaption("timerStartEvent");
		multipleStartEvent.addAdaption("escalationStartEvent");
		add(multipleStartEvent);
	}

	private void buildEventSubProcessInterruptingParallelMultipleStartEvent() {
		AdaptableElement parallelMultipleStartEvent = new AdaptableElement(
				"interruptingParallelMultipleStartEvent");
		parallelMultipleStartEvent
		.setLocatorExpression("//*[local-name() = 'subProcess' and @triggeredByEvent = 'true']/*[local-name() = 'startEvent' and (@isInterrupting = 'true') and (@parallelMultiple = 'true') and (count(child::*[contains(local-name(),'ventDefinition')]) > 1)]");
		parallelMultipleStartEvent
		.addAdaption("A multipleParallelStartEvent cannot be adapted since there is no other way to avoid the instantiation of a process unless multiple conditions are satisfied");
		add(parallelMultipleStartEvent);
	}

	private void buildEventSubProcessNonInterruptingParallelMultipleStartEvent() {
		AdaptableElement parallelMultipleStartEvent = new AdaptableElement(
				"nonInterruptingParallelMultipleStartEvent");
		parallelMultipleStartEvent
		.setLocatorExpression("//*[local-name() = 'subProcess' and @triggeredByEvent = 'true']/*[local-name() = 'startEvent' and not (@isInterrupting = 'true') and (@parallelMultiple = 'true') and (count(child::*[contains(local-name(),'ventDefinition')]) > 1)]");
		parallelMultipleStartEvent
		.addAdaption("A multipleParallelStartEvent cannot be adapted since there is no other way to avoid the instantiation of a process unless multiple conditions are satisfied");
		add(parallelMultipleStartEvent);
	}


	private void buildErrorBoundaryEvent() {
		AdaptableElement errorBoundaryEvent = new AdaptableElement(
				"errorBoundaryEvent");
		errorBoundaryEvent
		.setLocatorExpression(buildNonInterruptingBoundaryEventXPathExpression("error"));
		errorBoundaryEvent.addAdaption("messageBoundaryEvent");
		errorBoundaryEvent.addAdaption("escalationBoundaryEvent");
		errorBoundaryEvent.addAdaption("conditionalBoundaryEvent");
		errorBoundaryEvent.addAdaption("signalBoundaryEvent");
		errorBoundaryEvent.addAdaption("multipleBoundaryEvent");
		errorBoundaryEvent.addAdaption("multipleParallelBoundaryEvent");
		add(errorBoundaryEvent);
	}

	private void buildNoneEndEvent() {
		AdaptableElement noneEndEvent = new AdaptableElement("noneEndEvent");
		noneEndEvent
		.setLocatorExpression("//*[local-name() = 'endEvent' and not(child::*[contains(local-name(),'ventDefinition')])]");
		noneEndEvent.addAdaption("messageEndEvent");
		noneEndEvent.addAdaption("signalEndEvent");
		noneEndEvent.addAdaption("terminateEndEvent");
		noneEndEvent.addAdaption("multipleEndEvent");
		add(noneEndEvent);
	}

	private void buildMessageEndEvent() {
		AdaptableElement messageEndEvent = new AdaptableElement("messageEndEvent");
		messageEndEvent
		.setLocatorExpression(buildEndEventXPathExpression("message"));
		messageEndEvent.setDocumentation("A messageEndEvent can be adapted to another type of endEvent that can refer to normal termination and produces a trigger");
		messageEndEvent.addAdaption("signalEndEvent");
		messageEndEvent.addAdaption("multipleEndEvent");
		add(messageEndEvent);
	}

	private void buildErrorEndEvent() {
		AdaptableElement errorEndEvent = new AdaptableElement("errorEndEvent");
		errorEndEvent
		.setLocatorExpression(buildEndEventXPathExpression("error"));
		errorEndEvent.setDocumentation("There is hardly an equivalent for this end event, since it terminates all active threads and signals a fault. Therefore, only a combination of events can replace this event.");
		errorEndEvent.addAdaption("multipleEndEvent");
		add(errorEndEvent);
	}

	private void buildEscalationEndEvent() {
		AdaptableElement escalationEndEvent = new AdaptableElement("escalationEndEvent");
		escalationEndEvent
		.setLocatorExpression(buildEndEventXPathExpression("escalation"));
		escalationEndEvent.setDocumentation("There is hardly an equivalent for this end event, since it does not terminate all active threads and signals a problem. Therefore, only a combination of events can replace this event.");
		escalationEndEvent.addAdaption("multipleEndEvent");
		add(escalationEndEvent);
	}

	private void buildCancelEndEvent() {
		AdaptableElement cancelEndEvent = new AdaptableElement("cancelEndEvent");
		cancelEndEvent
		.setLocatorExpression(buildEndEventXPathExpression("cancel"));
		cancelEndEvent.setDocumentation("Since there is no alternative endEvent with transactional sematics, this event cannot be adapted");
		add(cancelEndEvent);
	}

	private void buildCompensationEndEvent() {
		AdaptableElement compensationEndEvent = new AdaptableElement("compensationEndEvent");
		compensationEndEvent
		.setLocatorExpression(buildEndEventXPathExpression("compensation"));
		compensationEndEvent.setDocumentation("Since there is no alternative endEvent with compensation sematics, this event cannot be adapted");
		add(compensationEndEvent);
	}

	private void buildSignalEndEvent() {
		AdaptableElement signalEndEvent = new AdaptableElement("signalEndEvent");
		signalEndEvent
		.setLocatorExpression(buildEndEventXPathExpression("signal"));
		signalEndEvent.setDocumentation("A signalEndEvent can be adapted to another type of endEvent that can refer to normal termination and produces a trigger");
		signalEndEvent.addAdaption("messageEndEvent");
		signalEndEvent.addAdaption("multipleEndEvent");
		add(signalEndEvent);
	}

	private void buildTerminateEndEvent() {
		AdaptableElement terminateEndEvent = new AdaptableElement("terminateEndEvent");
		terminateEndEvent
		.setLocatorExpression(buildEndEventXPathExpression("terminate"));
		terminateEndEvent.setDocumentation("Since there is no alternative endEvent that results in immediate termination without compensation or event handling, this event cannot be adapted");
		add(terminateEndEvent);
	}

	private void buildMultipleEndEvent() {
		AdaptableElement multipleEndEvent = new AdaptableElement("multipleEndEvent");
		multipleEndEvent
		.setLocatorExpression("//*[local-name() = 'endEvent' and not(@parallelMultiple = 'true') and (count(child::*[contains(local-name(),'ventDefinition')]) > 1)]");
		multipleEndEvent.setDocumentation("A multipleEndEvent can be reduced to one of the available alternatives");
		multipleEndEvent.addAdaption("noneEndEvent");
		multipleEndEvent.addAdaption("messageEndEvent");
		multipleEndEvent.addAdaption("errorEndEvent");
		multipleEndEvent.addAdaption("escalationEndEvent");
		multipleEndEvent.addAdaption("cancelEndEvent");
		multipleEndEvent.addAdaption("compensationEndEvent");
		multipleEndEvent.addAdaption("signalEndEvent");
		multipleEndEvent.addAdaption("terminateEndEvent");
		multipleEndEvent.addAdaption("multipleEndEvent");
		add(multipleEndEvent);
	}

	private void buildNoneStartEvent() {
		AdaptableElement noneStartEvent = new AdaptableElement("noneStartEvent");
		noneStartEvent
		.setLocatorExpression("/*[local-name() = 'process']/*[local-name() = 'startEvent' and not(/*[contains(local-name(),'ventDefinition')])]");
		noneStartEvent.addAdaption("messageStartEvent");
		noneStartEvent.addAdaption("conditionalStartEvent");
		noneStartEvent.addAdaption("signalStartEvent");
		noneStartEvent.addAdaption("multipleStartEvent");
		noneStartEvent.addAdaption("parallelMultipleStartEvent");
		add(noneStartEvent);
	}

	private void buildMessageStartEvent() {
		AdaptableElement messageStartEvent = new AdaptableElement(
				"messageStartEvent");
		messageStartEvent
		.setLocatorExpression(buildStartEventXPathExpression("message"));
		messageStartEvent
		.addAdaption("A messageStartEvent can be adapted to another startEvent that is triggered in some fashion.");
		messageStartEvent.addAdaption("conditionalStartEvent");
		messageStartEvent.addAdaption("signalStartEvent");
		messageStartEvent.addAdaption("multipleStartEvent");
		messageStartEvent.addAdaption("parallelMultipleStartEvent");
		add(messageStartEvent);
	}

	private void buildTimerStartEvent() {
		AdaptableElement timerStartEvent = new AdaptableElement(
				"timerStartEvent");
		timerStartEvent
		.setLocatorExpression(buildStartEventXPathExpression("timer"));
		timerStartEvent
		.addAdaption("A timerStartEvent can be adapted to another startEvent that is triggered in some fashion, as it is possible to calculate the expiration of the time and trigger the event when it does");
		timerStartEvent.addAdaption("conditionalStartEvent");
		timerStartEvent.addAdaption("messageStartEvent");
		timerStartEvent.addAdaption("signalStartEvent");
		timerStartEvent.addAdaption("multipleStartEvent");
		timerStartEvent.addAdaption("parallelMultipleStartEvent");
		add(timerStartEvent);
	}

	private void buildConditionalStartEvent() {
		AdaptableElement conditionalStartEvent = new AdaptableElement(
				"conditionalStartEvent");
		conditionalStartEvent
		.setLocatorExpression(buildStartEventXPathExpression("conditional"));
		conditionalStartEvent
		.addAdaption("A conditionalStartEvent can be adapted to another startEvent that is triggered in some fashion");
		conditionalStartEvent.addAdaption("messageStartEvent");
		conditionalStartEvent.addAdaption("signalStartEvent");
		conditionalStartEvent.addAdaption("multipleStartEvent");
		conditionalStartEvent.addAdaption("parallelMultipleStartEvent");
		add(conditionalStartEvent);
	}

	private void buildSignalStartEvent() {
		AdaptableElement signalStartEvent = new AdaptableElement(
				"signalStartEvent");
		signalStartEvent
		.setLocatorExpression(buildStartEventXPathExpression("signal"));
		signalStartEvent
		.addAdaption("A signalStartEvent can be adapted to another startEvent that is triggered in some fashion");
		signalStartEvent.addAdaption("messageStartEvent");
		signalStartEvent.addAdaption("conditionalStartEvent");
		signalStartEvent.addAdaption("multipleStartEvent");
		signalStartEvent.addAdaption("parallelMultipleStartEvent");
		add(signalStartEvent);
	}

	private void buildMultipleStartEvent() {
		AdaptableElement multipleStartEvent = new AdaptableElement(
				"multipleStartEvent");
		multipleStartEvent
		.setLocatorExpression("//*[local-name() = 'startEvent' and not(@parallelMultiple = 'true') and (count(child::*[contains(local-name(),'ventDefinition')]) > 1)]");
		multipleStartEvent
		.addAdaption("A multipleStartEvent can be reduced to one of the available alternative start events");
		multipleStartEvent.addAdaption("messageStartEvent");
		multipleStartEvent.addAdaption("conditionalStartEvent");
		multipleStartEvent.addAdaption("signalStartEvent");
		multipleStartEvent.addAdaption("timerStartEvent");
		multipleStartEvent.addAdaption("noneStartEvent");
		add(multipleStartEvent);
	}

	private void buildMultipleParallelStartEvent() {
		AdaptableElement multipleParallelStartEvent = new AdaptableElement(
				"multipleParallelStartEvent");
		multipleParallelStartEvent
		.setLocatorExpression("//*[local-name() = 'startEvent' and @parallelMultiple = 'true' and (count(child::*[contains(local-name(),'ventDefinition')]) > 1)]");
		multipleParallelStartEvent
		.addAdaption("A multipleParallelStartEvent cannot be adapted since there is no other way to avoid the instantiation of a process unless multiple conditions are satisfied");
		add(multipleParallelStartEvent);
	}

	private void buildSubProcessStartEvent() {
		AdaptableElement subProcessStartEvent = new AdaptableElement(
				"subProcessStartEvent");
		subProcessStartEvent
		.setLocatorExpression("//*[local-name() = 'subProcess' and not(@triggeredByEvent = 'true')]/*[local-name() = 'startEvent']");
		subProcessStartEvent
		.addAdaption("A startEvent of an ordinary subProcess cannot be adapted since it is the only way of starting non-eventSubProcesses");
		add(subProcessStartEvent);
	}

	private String buildStartEventXPathExpression(String eventType) {
		return buildEventXPathExpression("start", eventType);
	}

	private String buildEndEventXPathExpression(String eventType) {
		return buildEventXPathExpression("end", eventType);
	}

	private String buildIntermediateThrowEventXPathExpression(String eventType){
		return buildEventXPathExpression("intermediateThrow", eventType);
	}

	private String buildIntermediateCatchEventXPathExpression(String eventType){
		return buildEventXPathExpression("intermediateCatch", eventType);
	}

	private String buildEventSubProcessNonInterruptingStartEventXPathExpression(
			String eventType) {
		return "//*[local-name() = 'subProcess' and @triggeredByEvent = 'true']/*[local-name() = 'startEvent' and not(@isInterrupting = 'true') and (child::*[local-name() = '"
				+ eventType
				+ "EventDefinition'] or child::*[local-name() = 'eventDefinitionRef' and text() = //*[local-name() = '"
				+ eventType
				+ "EventDefinition']/@id]) and (count(child::*[contains(local-name(),'ventDefinition')]) = 1)]";
	}

	private String buildEventSubProcessInterruptingStartEventXPathExpression(
			String eventType) {
		return "//*[local-name() = 'subProcess' and @triggeredByEvent = 'true']/*[local-name() = 'startEvent' and @isInterrupting = 'true' and (child::*[local-name() = '"
				+ eventType
				+ "EventDefinition'] or child::*[local-name() = 'eventDefinitionRef' and text() = //*[local-name() = '"
				+ eventType
				+ "EventDefinition']/@id]) and (count(child::*[contains(local-name(),'ventDefinition')]) = 1)]";
	}

	private String buildNonInterruptingBoundaryEventXPathExpression(
			String eventType) {
		return "//*[local-name() = 'boundaryEvent' and not(@isInterrupting = 'true') and (child::*[local-name() = '"
				+ eventType
				+ "EventDefinition'] or child::*[local-name() = 'eventDefinitionRef' and text() = //*[local-name() = '"
				+ eventType
				+ "EventDefinition']/@id]) and (count(child::*[contains(local-name(),'ventDefinition')]) = 1)]";
	}

	private String buildInterruptingBoundaryEventXPathExpression(String eventType) {
		return "//*[local-name() = 'boundaryEvent' and @isInterrupting = 'true' and (child::*[local-name() = '"
				+ eventType
				+ "EventDefinition'] or child::*[local-name() = 'eventDefinitionRef' and text() = //*[local-name() = '"
				+ eventType
				+ "EventDefinition']/@id]) and (count(child::*[contains(local-name(),'ventDefinition')]) = 1)]";
	}

}
