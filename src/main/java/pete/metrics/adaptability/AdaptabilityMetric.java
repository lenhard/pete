package pete.metrics.adaptability;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

class AdaptabilityMetric {

	private Map<String, AdaptableElement> elements;

	private final static int REFERENCE_SCORE = 6;

	private AtomicInteger sum;

	private AtomicInteger maxDegree;

	public AdaptabilityMetric() {
		elements = new AdaptableElements().getElementsByName();
	}

	public double computeAdaptability(Map<String, AtomicInteger> processElements) {
		sum = new AtomicInteger(0);
		maxDegree = new AtomicInteger(0);
		processElements.keySet().forEach(
				elementName -> addElementAdaptability(processElements,
						elementName));
		if (maxDegree.get() == 0) {
			return 0;
		} else {
			return sum.doubleValue() / maxDegree.doubleValue();
		}
	}

	private void addElementAdaptability(
			Map<String, AtomicInteger> processElements, String elementName) {
		int number = processElements.get(elementName).get();
		int elementScore = elements.get(elementName).getAdaptabilityScore();
		sum.addAndGet(number * elementScore);
		maxDegree.addAndGet(number * REFERENCE_SCORE);
	}
}
