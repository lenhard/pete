package pete.executables;

import static org.junit.Assert.assertEquals;

import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.junit.Ignore;
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
		assertEquals("DE should be 244, but was " + de, 244, de);
	}

	@Test
	public void testAITComputation() throws ParseException {
		sut = new DirectoryAnalyzer(new AverageInstallationTimeCalculator());
		Report report = sut.analyzeDirectory(Paths.get(userDir
				+ "/src/test/resources/installability/server/simple/"));

		String ait = report.getEntries().get(0).getVariableValue("AIT");
		NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
		Number number = format.parse(ait);
		double d = number.doubleValue();
		assertEquals("AIT should be 22,00, but was " + d, 22, d, 0.1);
	}

	@Test
	public void testESRComputation() throws ParseException {
		sut = new DirectoryAnalyzer(new AverageInstallationTimeCalculator());
		Report report = sut.analyzeDirectory(Paths.get(userDir
				+ "/src/test/resources/installability/server/simple/"));

		String esr = report.getEntries().get(0).getVariableValue("ESR");
		NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
		Number number = format.parse(esr);
		double d = number.doubleValue();
		assertEquals("ESR should be 1,00, but was " + d, 1, d, 0.1);
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
	@Ignore
	public void testEmptyDir() {
		sut = new DirectoryAnalyzer(new AverageInstallationTimeCalculator());
		Report report = sut.analyzeDirectory(Paths.get(userDir
				+ "/src/test/resources/installability/server/empty/"));

		int numberOfEntries = report.getEntries().size();
		assertEquals("There should be no result, but was " + numberOfEntries,
				0, numberOfEntries);
	}

}
