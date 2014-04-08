package pete.metrics.adaptability.elements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

class GatewayElements {

	private final Collection<AdaptableElement> elements;

	public GatewayElements() {
		elements = new HashSet<>();
		buildExclusiveGateway();
		buildInclusiveGateway();
	}

	private void buildInclusiveGateway() {
		AdaptableElement inclusiveGateway = new AdaptableElement("inclusiveGateway");
		inclusiveGateway.setLocatorExpression("//*[local-name() = 'inclusiveGateway']");
		inclusiveGateway.addAdaption("parallelEventBasedGateway");
		inclusiveGateway.addAdaption("complexGateway");
		addToSet(inclusiveGateway);
	}

	private void buildExclusiveGateway() {
		AdaptableElement exclusiveGateway = new AdaptableElement("exclusiveGateway");
		exclusiveGateway.setLocatorExpression("//*[local-name() = 'exclusiveGateway']");
		exclusiveGateway.addAdaption("eventBasedGateway");
		exclusiveGateway.addAdaption("complexGateway");
		exclusiveGateway.addAdaption("inclusiveGateway");
		addToSet(exclusiveGateway);
	}

	private void addToSet(AdaptableElement element) {
		boolean success = elements.add(element);
		if (!success) {
			throw new IllegalStateException(element.getName()
					+ " was tried to be added twice");
		}
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
