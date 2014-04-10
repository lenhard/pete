package pete.metrics.adaptability.elements;


class DetectionOnlyElements extends ElementsCollection {

	public DetectionOnlyElements() {
		buildSequenceFlow();
	}

	private void buildSequenceFlow() {
		AdaptableElement sequenceFlow = new AdaptableElement("sequenceFlow");
		sequenceFlow.setLocatorExpression("//*[local-name() = 'sequenceFlow']");
		add(sequenceFlow);
	}

}
