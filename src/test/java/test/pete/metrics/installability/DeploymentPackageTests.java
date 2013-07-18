package test.pete.metrics.installability;

import static org.junit.Assert.assertEquals;

import java.nio.file.Paths;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import pete.metrics.installability.application.DeploymentPackageAnalyzer;
import pete.reporting.ReportEntry;

public class DeploymentPackageTests {

	private DeploymentPackageAnalyzer sut;

	private final String resourcePaths = System.getProperty("user.dir")
			+ "/src/test/resources/installability/deployment/";

	@Before
	public void setUp() {
		sut = new DeploymentPackageAnalyzer();
	}

	@Test
	public void testNonArchive() {
		List<ReportEntry> result = sut.analyzeFile(Paths.get(resourcePaths
				+ "empty"));
		assertEquals(0, result.size());
	}

	@Test
	public void testArchiveWithXmlDescriptor() {
		ReportEntry result = sut.analyzeFile(
				Paths.get(resourcePaths + "onlyXmlDescriptor.zip")).get(0);
		assertEquals(14, getDeploymentEffort(result));
		assertEquals(2, getEffortOfPackageConstruction(result));
		assertEquals(12, getDescriptorComplexity(result));
	}

	@Test
	public void testArchiveWithTextFile() {
		ReportEntry result = sut.analyzeFile(
				Paths.get(resourcePaths + "onlyManifest.zip")).get(0);
		assertEquals(7, getDeploymentEffort(result));
		assertEquals(2, getEffortOfPackageConstruction(result));
		assertEquals(5, getDescriptorComplexity(result));
	}

	@Test
	public void testArchiveWithXmlAndText() {
		ReportEntry result = sut.analyzeFile(
				Paths.get(resourcePaths + "xmlAndText.zip")).get(0);
		assertEquals(20, getDeploymentEffort(result));
		assertEquals(3, getEffortOfPackageConstruction(result));
		assertEquals(17, getDescriptorComplexity(result));
	}

	@Test
	public void testArchiveWithXmlAndTextAndIgnores() {
		ReportEntry result = sut.analyzeFile(
				Paths.get(resourcePaths + "xmlAndTextAndIgnores.zip")).get(0);
		assertEquals(20, getDeploymentEffort(result));
		assertEquals(3, getEffortOfPackageConstruction(result));
		assertEquals(17, getDescriptorComplexity(result));
	}

	@Test
	public void testNestedArchives() {
		ReportEntry result = sut.analyzeFile(
				Paths.get(resourcePaths + "nestedArchives.zip")).get(0);
		assertEquals(40, getDeploymentEffort(result));
		assertEquals(6, getEffortOfPackageConstruction(result));
		assertEquals(34, getDescriptorComplexity(result));
	}

	@Test
	public void testOdeSample() {
		ReportEntry result = sut.analyzeFile(
				Paths.get(resourcePaths + "TestAlarm.zip")).get(0);
		assertEquals(17, getDeploymentEffort(result));
		assertEquals(2, getEffortOfPackageConstruction(result));
		assertEquals(15, getDescriptorComplexity(result));
	}

	@Test
	public void testComplexArchive() {
		ReportEntry result = sut.analyzeFile(
				Paths.get(resourcePaths + "Sequence.jar")).get(0);
		assertEquals(27, getDeploymentEffort(result));
		assertEquals(5, getEffortOfPackageConstruction(result));
		assertEquals(22, getDescriptorComplexity(result));

		result = sut.analyzeFile(
				Paths.get(resourcePaths + "sun-http-binding.jar")).get(0);
		assertEquals(25, getDeploymentEffort(result));
		assertEquals(6, getEffortOfPackageConstruction(result));
		assertEquals(19, getDescriptorComplexity(result));

		result = sut.analyzeFile(
				Paths.get(resourcePaths + "SequenceApplication.zip")).get(0);
		assertEquals(95, getDeploymentEffort(result));
		assertEquals(15, getEffortOfPackageConstruction(result));
		assertEquals(80, getDescriptorComplexity(result));
	}

	private int getDeploymentEffort(ReportEntry entry) {
		if (entry != null) {
			return Integer.parseInt(entry.getVariableValue("deploymentEffort"));
		} else {
			return 0;
		}
	}

	private int getEffortOfPackageConstruction(ReportEntry entry) {
		if (entry != null) {
			return Integer.parseInt(entry.getVariableValue("EPC"));
		} else {
			return 0;
		}
	}

	private int getDescriptorComplexity(ReportEntry entry) {
		if (entry != null) {
			return Integer.parseInt(entry.getVariableValue("DDS"));
		} else {
			return 0;
		}
	}

}
