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

	@Test
	public void testDeployability() {
		sut = new DirectoryAnalyzer(new DeploymentPackageAnalyzer());
		Report report = sut.analyzeDirectory(Paths
				.get("src/test/resources/installability/deployment"));
		int de = report.getSummedVariable("DE");
		assertEquals("DE should be 214, but was " + de, 214, de);
	}

	@Test
	public void testServerInstallability() {
		sut = new DirectoryAnalyzer(new AverageInstallationTimeCalculator());
		Report report = sut.analyzeDirectory(Paths
				.get("src/test/resources/installability/server/"));

		String ait = report.getEntries().get(0).getVariableValue("AIT");
		assertEquals("AIT should be 2,88, but was ", "2,88", ait);

		String esr = report.getEntries().get(0).getVariableValue("ESR");
		assertEquals("ESR should be 1,00, but was " + esr, "1,00", esr);
	}

}
