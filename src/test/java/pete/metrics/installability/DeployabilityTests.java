package pete.metrics.installability;

import static org.junit.Assert.assertEquals;

import java.nio.file.Paths;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import pete.metrics.installability.deployability.DeploymentPackageAnalyzer;
import pete.reporting.ReportEntry;

public class DeployabilityTests {

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
		assertEquals(13, getDeploymentEffort(result));
		assertEquals(2, getEffortOfPackageConstruction(result));
		assertEquals(11, getDescriptorComplexity(result));
	}

	@Test
	public void testArchiveWithXmlDescriptorWithText() {
		ReportEntry result = sut.analyzeFile(
				Paths.get(resourcePaths + "xmlDescriptorWithText.zip")).get(0);
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
		assertEquals(19, getDeploymentEffort(result));
		assertEquals(3, getEffortOfPackageConstruction(result));
		assertEquals(16, getDescriptorComplexity(result));
	}

	@Test
	public void testArchiveWithXmlAndTextAndIgnores() {
		ReportEntry result = sut.analyzeFile(
				Paths.get(resourcePaths + "xmlAndTextAndIgnores.zip")).get(0);
		assertEquals(19, getDeploymentEffort(result));
		assertEquals(3, getEffortOfPackageConstruction(result));
		assertEquals(16, getDescriptorComplexity(result));
	}

	@Test
	public void testNestedArchives() {
		ReportEntry result = sut.analyzeFile(
				Paths.get(resourcePaths + "nestedArchives.zip")).get(0);
		assertEquals(38, getDeploymentEffort(result));
		assertEquals(6, getEffortOfPackageConstruction(result));
		assertEquals(32, getDescriptorComplexity(result));
	}

	@Test
	public void testOdeSample() {
		ReportEntry result = sut.analyzeFile(
				Paths.get(resourcePaths + "TestAlarm.zip")).get(0);
		assertEquals(11, getDeploymentEffort(result));
		assertEquals(2, getEffortOfPackageConstruction(result));
		assertEquals(9, getDescriptorComplexity(result));
	}

	@Test
	public void testComplexArchive() {
		ReportEntry result = sut.analyzeFile(
				Paths.get(resourcePaths + "Sequence.jar")).get(0);
		assertEquals(22, getDeploymentEffort(result));
		assertEquals(5, getEffortOfPackageConstruction(result));
		assertEquals(17, getDescriptorComplexity(result));

		result = sut.analyzeFile(
				Paths.get(resourcePaths + "sun-http-binding.jar")).get(0);
		assertEquals(16, getDeploymentEffort(result));
		assertEquals(5, getEffortOfPackageConstruction(result));
		assertEquals(11, getDescriptorComplexity(result));

		result = sut.analyzeFile(
				Paths.get(resourcePaths + "SequenceApplication.zip")).get(0);
		assertEquals(85, getDeploymentEffort(result));
		assertEquals(14, getEffortOfPackageConstruction(result));
		assertEquals(71, getDescriptorComplexity(result));
	}

	private int getDeploymentEffort(ReportEntry entry) {
		if (entry != null) {
			return Integer.parseInt(entry.getVariableValue("DE"));
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
