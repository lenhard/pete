package pete.metrics.adaptability.elements;

class GatewayElements extends ElementsCollection {

	public GatewayElements() {
		buildExclusiveGateway();
		buildInclusiveGateway();
		buildParallelGateway();
		buildComplexGateway();
		buildEventBasedGateway();
		buildParallelEventBasedGateway();
		buildInstantiatingParallelEventBasedGateway();
		buildInstantiatingEventBasedGateway();
	}

	private void buildParallelEventBasedGateway() {
		AdaptableElement eventBasedGateway = new AdaptableElement(
				"parallelEventBasedGateway");
		eventBasedGateway
				.setDocumentation("A parallel eventBasedGateway can be adapted to a solution where the events are processed through "
						+ "catch events before the gateway");
		eventBasedGateway
				.setLocatorExpression("//*[local-name() = 'eventBasedGateway' and (@eventGatewayType = 'parallel') and not (@instantiate = 'true')]");
		eventBasedGateway.addAdaption("inclusiveGateway");
		eventBasedGateway.addAdaption("complexGateway");
		add(eventBasedGateway);
	}

	private void buildInstantiatingParallelEventBasedGateway() {
		AdaptableElement eventBasedGateway = new AdaptableElement(
				"instantiatingParallelEventBasedGateway");
		eventBasedGateway
				.setDocumentation("An instantiating parallel eventBasedGateway cannot be adapted since there is no way to avoid "
						+ "the instantiation of the process until multiple events have been received");
		eventBasedGateway
				.setLocatorExpression("//*[local-name() = 'eventBasedGateway' and (@eventGatewayType = 'parallel') and (@instantiate = 'true')]");
		add(eventBasedGateway);
	}

	private void buildEventBasedGateway() {
		AdaptableElement eventBasedGateway = new AdaptableElement(
				"eventBasedGateway");
		eventBasedGateway
				.setDocumentation("An eventBasedGateway can be adapted to a solution where an intermediate multipleCatchEvent waits for the occurence of one among a set of events "
						+ "and a different control-flow path is taken by a following gateway depending on the type of event");
		eventBasedGateway
				.setLocatorExpression("//*[local-name() = 'eventBasedGateway' and not (@eventGatewayType = 'parallel') and not (@instantiate = 'true')]");
		eventBasedGateway
				.addAdaption("intermediateMultipleCatchEventFollowedByExclusiveGateway");
		eventBasedGateway
				.addAdaption("intermediateMultipleCatchEventFollowedByInclusiveGateway");
		eventBasedGateway
				.addAdaption("intermediateMultipleCatchEventFollowedByComplexGateway");
		add(eventBasedGateway);
	}

	private void buildInstantiatingEventBasedGateway() {
		AdaptableElement eventBasedGateway = new AdaptableElement(
				"instantiatingEventBasedGateway");
		eventBasedGateway
				.setDocumentation("An instantiating eventBasedGateway can be adapted to a solution where the multiple startEvents "
						+ "are merged by an exclusiveGateway");
		eventBasedGateway
				.setLocatorExpression("//*[local-name() = 'eventBasedGateway' and not (@eventGatewayType = 'parallel') and (@instantiate = 'true')]");
		eventBasedGateway.addAdaption("startEventsFollowedByExclusiveGateway");
		add(eventBasedGateway);
	}

	private void buildComplexGateway() {
		AdaptableElement complexGateway = new AdaptableElement("complexGateway");
		complexGateway
				.setLocatorExpression("//*[local-name() = 'complexGateway']");
		complexGateway
				.setDocumentation("The behaviour of the complexGateway cannot be replaced by any other construct");
		add(complexGateway);
	}

	private void buildParallelGateway() {
		AdaptableElement parallelGateway = new AdaptableElement(
				"parallelGateway");
		parallelGateway
				.setLocatorExpression("//*[local-name() = 'parallelGateway']");
		parallelGateway
				.setDocumentation("A parallelGateway can be replaced by other gateways that allow for parallelism, given the "
						+ "conditions of these gateways are set to trigger all branches");
		parallelGateway.addAdaption("inclusiveGateway");
		parallelGateway.addAdaption("complexGateway");
		add(parallelGateway);
	}

	private void buildInclusiveGateway() {
		AdaptableElement inclusiveGateway = new AdaptableElement(
				"inclusiveGateway");
		inclusiveGateway
				.setLocatorExpression("//*[local-name() = 'inclusiveGateway']");
		inclusiveGateway
				.setDocumentation("An inclusiveGateway can only be adapted to a complexGateway which is strictly more expressive");
		inclusiveGateway.addAdaption("complexGateway");
		add(inclusiveGateway);
	}

	private void buildExclusiveGateway() {
		AdaptableElement exclusiveGateway = new AdaptableElement(
				"exclusiveGateway");
		exclusiveGateway
				.setLocatorExpression("//*[local-name() = 'exclusiveGateway']");
		exclusiveGateway
				.setDocumentation("an exclusiveGateway can be adapted to any other gateway that allows for the triggering of "
						+ "one among a set of branches");
		exclusiveGateway.addAdaption("eventBasedGateway");
		exclusiveGateway.addAdaption("complexGateway");
		exclusiveGateway.addAdaption("inclusiveGateway");
		add(exclusiveGateway);
	}

}
