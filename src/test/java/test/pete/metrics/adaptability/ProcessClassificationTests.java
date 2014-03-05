package test.pete.metrics.adaptability;

import static org.junit.Assert.assertEquals;

import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import pete.metrics.adaptability.AdaptabilityAnalyzer;
import pete.reporting.ReportEntry;

public class ProcessClassificationTests {

	private AdaptabilityAnalyzer sut;

	@Before
	public void setUp() {
		sut = new AdaptabilityAnalyzer();
	}

	@Test
	public void detectsCorrectNamespace() {
		ReportEntry result = sut
				.analyzeFile(
						Paths.get("src/test/resources/adaptability/CorrectNamespace.bpmn"))
				.get(0);
		assertEquals("true", result.getVariableValue("isCorrectNamespace"));
	}

	@Test
	public void detectsIncorrectNamespace() {
		ReportEntry result = sut
				.analyzeFile(
						Paths.get("src/test/resources/adaptability/IncorrectNamespace.bpmn"))
				.get(0);
		assertEquals("false", result.getVariableValue("isCorrectNamespace"));
	}

	@Test
	public void detectsExecutableProcess() {
		ReportEntry result = sut
				.analyzeFile(
						Paths.get("src/test/resources/adaptability/ExecutableProcess.bpmn"))
				.get(0);
		assertEquals("true", result.getVariableValue("isExecutable"));
	}

	@Test
	public void detectsNonExecutableProcess() {
		ReportEntry result = sut
				.analyzeFile(
						Paths.get("src/test/resources/adaptability/NotExecutable.bpmn"))
				.get(0);
		assertEquals("false", result.getVariableValue("isExecutable"));
	}

}
