package test.pete.metrics.installability;

import static org.junit.Assert.assertEquals;

import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import pete.metrics.installability.DeploymentPackageAnalyzer;
import pete.reporting.ReportEntry;

public class DeploymentPackageTests {

	private DeploymentPackageAnalyzer sut;

	private final String resourcePaths = System.getProperty("user.dir")
			+ "/src/test/resources/installability/";

	@Before
	public void setUp() {
		sut = new DeploymentPackageAnalyzer();
	}

	@Test
	public void testNonArchive() {
		ReportEntry result = sut
				.analyzeFile(Paths.get(resourcePaths + "empty"));
		assertEquals(0, getPackageComplexity(result));
	}

	@Test
	public void testArchiveWithXmlDescriptor() {
		ReportEntry result = sut.analyzeFile(Paths.get(resourcePaths
				+ "onlyXmlDescriptor.zip"));
		assertEquals(14, getPackageComplexity(result));
	}

	@Test
	public void testArchiveWithTextFile() {
		ReportEntry result = sut.analyzeFile(Paths.get(resourcePaths
				+ "onlyManifest.zip"));
		assertEquals(7, getPackageComplexity(result));
	}

	@Test
	public void testArchiveWithXmlAndText() {
		ReportEntry result = sut.analyzeFile(Paths.get(resourcePaths
				+ "xmlAndText.zip"));
		assertEquals(20, getPackageComplexity(result));
	}

	@Test
	public void testArchiveWithXmlAndTextAndIgnores() {
		ReportEntry result = sut.analyzeFile(Paths.get(resourcePaths
				+ "xmlAndTextAndIgnores.zip"));
		assertEquals(20, getPackageComplexity(result));
	}

	@Test
	public void testNestedArchives() {
		ReportEntry result = sut.analyzeFile(Paths.get(resourcePaths
				+ "nestedArchives.zip"));
		assertEquals(40, getPackageComplexity(result));
	}

	private int getPackageComplexity(ReportEntry entry) {
		if (entry != null) {
			return entry.getVariableValue("packageComplexity");
		} else {
			return 0;
		}
	}

}
