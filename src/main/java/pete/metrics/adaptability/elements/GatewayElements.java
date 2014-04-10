package pete.metrics.adaptability.elements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

class GatewayElements implements ElementsCollection{

	private final Collection<AdaptableElement> elements;

	public GatewayElements() {
		elements = new HashSet<>();
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
		addToSet(eventBasedGateway);
	}

	private void buildEventBasedGateway() {
		AdaptableElement eventBasedGateway = new AdaptableElement("eventBasedGateway");
		eventBasedGateway.setDocumentation("An eventBasedGateway can be adapted to a solution where the events are processed through tasks before the gateway and set conditions to be evaluated by the gateway");
		eventBasedGateway.setLocatorExpression("//*[local-name() = 'eventBasedGateway' and not (@eventGatewayType = 'parallel')]");
		eventBasedGateway.addAdaption("parallelEventBasedGateway");
		eventBasedGateway.addAdaption("inclusiveGateway");
		eventBasedGateway.addAdaption("complexGateway");
		eventBasedGateway.addAdaption("exclusiveGateway");
		addToSet(eventBasedGateway);
	}

	private void buildComplexGateway() {
		AdaptableElement complexGateway = new AdaptableElement("complexGateway");
		complexGateway.setLocatorExpression("//*[local-name() = 'complexGateway']");
		complexGateway.addAdaption("parallelEventBasedGateway");
		addToSet(complexGateway);
	}

	private void buildParallelGateway() {
		AdaptableElement parallelGateway = new AdaptableElement("parallelGateway");
		parallelGateway.setLocatorExpression("//*[local-name() = 'parallelGateway']");
		parallelGateway.addAdaption("parallelEventBasedGateway");
		parallelGateway.addAdaption("inclusiveGateway");
		parallelGateway.addAdaption("complexGateway");
		addToSet(parallelGateway);
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
