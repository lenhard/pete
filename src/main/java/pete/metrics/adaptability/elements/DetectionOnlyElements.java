package pete.metrics.adaptability.elements;


class DetectionOnlyElements extends ElementsCollection {

	private EventElements events;

	public DetectionOnlyElements() {
		events = new EventElements();
		buildSequenceFlow();
		buildThrowLinkEvent();
		buildCatchLinkEvent();
	}

	private void buildSequenceFlow() {
		AdaptableElement sequenceFlow = new AdaptableElement("sequenceFlow", true);
		sequenceFlow.setLocatorExpression("//*[local-name() = 'sequenceFlow']");
		add(sequenceFlow);
	}

	private void buildThrowLinkEvent() {
		AdaptableElement linkEvent = new AdaptableElement("linkThrowEvent", true);
		linkEvent.setLocatorExpression(events.buildEventXPathExpression("intermediateThrowEvent", "link"));
		add(linkEvent);
	}

	private void buildCatchLinkEvent() {
		AdaptableElement linkEvent = new AdaptableElement("linkCatchEvent", true);
		linkEvent.setLocatorExpression(events.buildEventXPathExpression("intermediateCatchEvent", "link"));
		add(linkEvent);
	}

}
