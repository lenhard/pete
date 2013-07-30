package pete.metrics.portability;

import java.util.List;
import java.util.Map;

import pete.metrics.portability.assertions.Warning;

public class BasicPortabilityMetric implements PortabilityMetric {

	private int numOfElements;

	private Map<Integer, List<Warning>> warningsPerLine;

	public BasicPortabilityMetric(int numOfElements,
			Map<Integer, List<Warning>> warningsPerLine) {
		this.numOfElements = numOfElements;
		this.warningsPerLine = warningsPerLine;
	}

	@Override
	public double compute() {
		double basicMeasure = 1D - (double) warningsPerLine.keySet().size()
				/ (double) numOfElements;
		return basicMeasure;
	}

	public static String getLabel() {
		return "Mb";
	}

}
