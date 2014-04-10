package pete.metrics.adaptability;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import pete.metrics.adaptability.elements.AdaptableElement;
import pete.metrics.adaptability.elements.AdaptableElements;

public class WeightedAdaptabilityMetric implements AdaptabilityMetric {
	private Map<String, AdaptableElement> elements;

	private int referenceScore;

	private AtomicInteger sum;

	private AtomicInteger maxDegree;

	public WeightedAdaptabilityMetric() {
		elements = new AdaptableElements().getElementsByName();
		referenceScore = elements.values().parallelStream()
				.mapToInt(element -> element.getAdaptabilityScore()).max()
				.getAsInt();
	}

	@Override
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
		maxDegree.addAndGet(number * referenceScore);
	}
}
