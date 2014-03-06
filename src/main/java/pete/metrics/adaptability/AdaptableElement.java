package pete.metrics.adaptability;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class AdaptableElement {

	private final String name;

	private final List<String> adaptions;

	public AdaptableElement(String name) {
		this.name = name;
		adaptions = new ArrayList<>();
	}

	public void addAdaption(String adaption) {
		adaptions.add(adaption);
	}

	public List<String> getAdaptions() {
		return Collections.unmodifiableList(adaptions);
	}

	public int getNumberOfAdaptions() {
		return adaptions.size();
	}

	public String getName() {
		return name;
	}

}
