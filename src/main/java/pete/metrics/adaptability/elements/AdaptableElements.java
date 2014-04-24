package pete.metrics.adaptability.elements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public final class AdaptableElements extends ElementsCollection{

	private final Collection<AdaptableElement> elements;

	public AdaptableElements() {
		elements = new HashSet<>();

		elements.addAll(new ActivityElements().getElements());

		elements.addAll(new EventElements().getElements());

		elements.addAll(new GatewayElements().getElements());

		elements.addAll(new DetectionOnlyElements().getElements());
	}

	@Override
	public List<String> getElementNames() {
		List<String> result = new ArrayList<>(elements.size());
		elements.forEach(element -> result.add(element.getName()));
		return result;
	}

	@Override
	public List<AdaptableElement> getElements() {
		List<AdaptableElement> result = new ArrayList<>(elements.size());
		elements.forEach(element -> result.add(element));
		return result;
	}

	@Override
	public Map<String, AdaptableElement> getElementsByName() {
		HashMap<String, AdaptableElement> result = new HashMap<>();
		elements.forEach(element -> result.put(element.getName(), element));
		return result;
	}

}
