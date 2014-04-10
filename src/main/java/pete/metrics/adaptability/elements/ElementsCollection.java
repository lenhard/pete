package pete.metrics.adaptability.elements;

import java.util.List;
import java.util.Map;

interface ElementsCollection {

	public List<String> getElementNames();

	public List<AdaptableElement> getElements();

	public Map<String, AdaptableElement> getElementsByName();

}
