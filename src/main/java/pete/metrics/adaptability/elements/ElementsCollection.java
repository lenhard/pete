package pete.metrics.adaptability.elements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

abstract class ElementsCollection {

	private final Collection<AdaptableElement> elements;

	public ElementsCollection() {
		elements = new HashSet<>();
	}

	void add(AdaptableElement element) {
		boolean success = elements.add(element);
		if (!success) {
			throw new IllegalStateException(element.getName()
					+ " was tried to be added twice");
		}
	}

	void addAll(Collection<AdaptableElement> elements){
		elements.forEach(element -> add(element));
	}

	public List<String> getElementNames() {
		List<String> result = new ArrayList<>(elements.size());
		elements.forEach(element -> result.add(element.getName()));
		return result;
	}

	public List<AdaptableElement> getElements() {
		List<AdaptableElement> result = new ArrayList<>(elements.size());
		elements.forEach(element -> result.add(element));
		return result;
	}

	public Map<String, AdaptableElement> getElementsByName() {
		HashMap<String, AdaptableElement> result = new HashMap<>();
		elements.forEach(element -> result.put(element.getName(), element));
		return result;
	}

}
