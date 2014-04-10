package pete.metrics.adaptability.elements;


class GatewayElements extends ElementsCollection{

	public GatewayElements() {
		buildExclusiveGateway();
		buildInclusiveGateway();
		buildParallelGateway();
		buildComplexGateway();
		buildEventBasedGateway();
		buildParallelEventBasedGateway();
	}

	private void buildParallelEventBasedGateway() {
		AdaptableElement eventBasedGateway = new AdaptableElement("parallelEventBasedGateway");
		eventBasedGateway.setDocumentation("A parallel eventBasedGateway can be adapted to a solution where the events are processed through tasks before the gateway and set conditions to be evaluated by the gateway");
		eventBasedGateway.setLocatorExpression("//*[local-name() = 'eventBasedGateway' and (@eventGatewayType = 'parallel')]");
		eventBasedGateway.addAdaption("inclusiveGateway");
		eventBasedGateway.addAdaption("complexGateway");
		add(eventBasedGateway);
	}

	private void buildEventBasedGateway() {
		AdaptableElement eventBasedGateway = new AdaptableElement("eventBasedGateway");
		eventBasedGateway.setDocumentation("An eventBasedGateway can be adapted to a solution where the events are processed through tasks before the gateway and set conditions to be evaluated by the gateway");
		eventBasedGateway.setLocatorExpression("//*[local-name() = 'eventBasedGateway' and not (@eventGatewayType = 'parallel')]");
		eventBasedGateway.addAdaption("parallelEventBasedGateway");
		eventBasedGateway.addAdaption("inclusiveGateway");
		eventBasedGateway.addAdaption("complexGateway");
		eventBasedGateway.addAdaption("exclusiveGateway");
		add(eventBasedGateway);
	}

	private void buildComplexGateway() {
		AdaptableElement complexGateway = new AdaptableElement("complexGateway");
		complexGateway.setLocatorExpression("//*[local-name() = 'complexGateway']");
		complexGateway.addAdaption("parallelEventBasedGateway");
		add(complexGateway);
	}

	private void buildParallelGateway() {
		AdaptableElement parallelGateway = new AdaptableElement("parallelGateway");
		parallelGateway.setLocatorExpression("//*[local-name() = 'parallelGateway']");
		parallelGateway.addAdaption("parallelEventBasedGateway");
		parallelGateway.addAdaption("inclusiveGateway");
		parallelGateway.addAdaption("complexGateway");
		add(parallelGateway);
	}

	private void buildInclusiveGateway() {
		AdaptableElement inclusiveGateway = new AdaptableElement("inclusiveGateway");
		inclusiveGateway.setLocatorExpression("//*[local-name() = 'inclusiveGateway']");
		inclusiveGateway.addAdaption("parallelEventBasedGateway");
		inclusiveGateway.addAdaption("complexGateway");
		add(inclusiveGateway);
	}

	private void buildExclusiveGateway() {
		AdaptableElement exclusiveGateway = new AdaptableElement("exclusiveGateway");
		exclusiveGateway.setLocatorExpression("//*[local-name() = 'exclusiveGateway']");
		exclusiveGateway.addAdaption("eventBasedGateway");
		exclusiveGateway.addAdaption("complexGateway");
		exclusiveGateway.addAdaption("inclusiveGateway");
		add(exclusiveGateway);
	}

}
