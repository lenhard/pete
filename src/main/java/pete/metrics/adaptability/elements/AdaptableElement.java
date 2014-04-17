package pete.metrics.adaptability.elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

public final class AdaptableElement {

	private final String name;

	private final List<String> adaptions;

	private String documentation;

	private String locatorExpression = "//*[local-name() = 'definitions']";

	private volatile int hashCode;

	private final boolean forDetectionOnly;

	public AdaptableElement(String name) {
		checkName(name);

		this.name = name;
		adaptions = new ArrayList<>();
		documentation = "";
		forDetectionOnly = false;
	}

	public AdaptableElement(String name, boolean isForDetectionOnly){
		checkName(name);

		this.name = name;
		adaptions = new ArrayList<>();
		documentation = "";
		forDetectionOnly = isForDetectionOnly;
	}

	private void checkName(String name) {
		if (name == null) {
			throw new IllegalArgumentException("name must not be null");
		}
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
			checkXPathSyntax(locatorExpression);
			this.locatorExpression += locatorExpression;
		}
	}

	private void checkXPathSyntax(String expression){
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		try {
			XPathExpression expr = xpath.compile(expression);
		} catch (XPathExpressionException e) {
			throw new IllegalArgumentException(e);
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

	public boolean isForDetectionOnly(){
		return forDetectionOnly;
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
