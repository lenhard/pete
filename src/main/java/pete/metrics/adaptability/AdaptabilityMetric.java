package pete.metrics.adaptability;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

class AdaptabilityMetric {

	private Map<String, AdaptableElement> elements;

	private final static int REFERENCE_SCORE = 6;

	public AdaptabilityMetric() {
		elements = new RelevantElements().getElementsByName();
	}

	public double computeAdaptability(Map<String, AtomicInteger> processElements) {
		int sum = 0;
		int maxDegree = 0;
		for (String elementName : processElements.keySet()) {
			int number = processElements.get(elementName).get();
			int elementScore = elements.get(elementName).getAdaptabilityScore();
			sum += (number * elementScore);
			maxDegree += (number * REFERENCE_SCORE);
		}
		double result = ((double) sum) / ((double) maxDegree);
		return result;
	}
}
