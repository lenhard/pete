package test.pete.executables;

import static org.junit.Assert.assertEquals;

import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import pete.executables.DirectoryAnalyzer;
import pete.metrics.installability.application.DeploymentPackageAnalyzer;
import pete.reporting.Report;

public class DirectoryAnalyzerTests {

	private DirectoryAnalyzer sut;

	@Before
	public void setUp() {
		sut = new DirectoryAnalyzer(new DeploymentPackageAnalyzer());
	}

	@Test
	public void testResourcesDirectory() {
		Report report = sut.analyzeDirectory(Paths
				.get("src/test/resources/installability"));
		assertEquals(248, report.getSummedVariable("packageComplexity"));
	}

}
