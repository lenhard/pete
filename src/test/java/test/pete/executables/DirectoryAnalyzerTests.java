package test.pete.executables;

import static org.junit.Assert.assertEquals;

import java.nio.file.Paths;

import org.junit.Test;

import pete.executables.DirectoryAnalyzer;
import pete.metrics.installability.deployability.DeploymentPackageAnalyzer;
import pete.metrics.installability.server.AverageInstallationTimeCalculator;
import pete.reporting.Report;

public class DirectoryAnalyzerTests {

	private DirectoryAnalyzer sut;

	private String userDir = System.getProperty("user.dir");

	@Test
	public void testDeployability() {
		sut = new DirectoryAnalyzer(new DeploymentPackageAnalyzer());
		Report report = sut.analyzeDirectory(Paths.get(userDir
				+ "/src/test/resources/installability/deployment/"));
		int de = report.getSummedVariable("DE");
		assertEquals("DE should be 214, but was " + de, 214, de);
	}

	@Test
	public void testAITComputation() {
		sut = new DirectoryAnalyzer(new AverageInstallationTimeCalculator());
		Report report = sut.analyzeDirectory(Paths.get(userDir
				+ "/src/test/resources/installability/server/simple/"));

		String ait = report.getEntries().get(0).getVariableValue("AIT");
		assertEquals("AIT should be 22,00, but was ", "22,00", ait);
	}

	@Test
	public void testESRComputation() {
		sut = new DirectoryAnalyzer(new AverageInstallationTimeCalculator());
		Report report = sut.analyzeDirectory(Paths.get(userDir
				+ "/src/test/resources/installability/server/simple/"));

		String esr = report.getEntries().get(0).getVariableValue("ESR");
		assertEquals("ESR should be 1,00, but was " + esr, "1,00", esr);
	}

	@Test
	public void testOnlyRelevantFilesAreListed() {
		sut = new DirectoryAnalyzer(new AverageInstallationTimeCalculator());
		Report report = sut.analyzeDirectory(Paths.get(userDir
				+ "/src/test/resources/installability/server/advanced/"));

		int numberOfEntries = report.getEntries().size();
		assertEquals("Should only list one file, but was " + numberOfEntries,
				1, numberOfEntries);
	}

	@Test
	public void testEmptyDir() {
		sut = new DirectoryAnalyzer(new AverageInstallationTimeCalculator());
		Report report = sut.analyzeDirectory(Paths.get(userDir
				+ "/src/test/resources/installability/server/empty/"));

		int numberOfEntries = report.getEntries().size();
		assertEquals("There should be no result, but was " + numberOfEntries,
				0, numberOfEntries);
	}

}
