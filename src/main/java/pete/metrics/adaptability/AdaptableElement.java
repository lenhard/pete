package pete.metrics.adaptability;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class AdaptableElement {

	private final String name;

	private final List<String> adaptions;

	private String documentation;

	private String locatorExpression = "//*[local-name() = 'definitions']";

	private volatile int hashCode;

	public AdaptableElement(String name) {
		if (name == null) {
			throw new IllegalArgumentException("name must not be null");
		}

		this.name = name;
		adaptions = new ArrayList<>();
		documentation = "";
	}

	public void addAdaption(String adaption) {
		if (adaption != null) {
			adaptions.add(adaption);
		}
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

	public int getAdaptabilityScore() {
		return adaptions.size();
	}

	public String getLocatorExpression() {
		return locatorExpression;
	}

	public void setLocatorExpression(String locatorExpression) {
		if (!(locatorExpression == null)) {
			this.locatorExpression += locatorExpression;
		}
	}

	public String getDocumentation() {
		return documentation;
	}

	public void setDocumentation(String documentation) {
		if (documentation == null) {
			this.documentation = "";
		} else {
			this.documentation = documentation;
		}
	}

	@Override
	public boolean equals(Object o){
		if(o == this){
			return true;
		} else if(o instanceof AdaptableElement){
			AdaptableElement element = (AdaptableElement) o;
			if(element.name.equals(this.name)){
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode(){
		int result = hashCode;
		if(result == 0){
			result = 19;
			result = 37 * result + name.hashCode();
			hashCode = result;
		}
		return result;
	}

}
