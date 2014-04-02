package pete.metrics.installability;

import static org.junit.Assert.assertEquals;

import java.nio.file.Paths;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import pete.metrics.installability.server.AverageInstallationTimeCalculator;
import pete.reporting.ReportEntry;

public class ServerInstallabilityMetricsTests {

	private AverageInstallationTimeCalculator sut;

	private final String resourcePaths = System.getProperty("user.dir")
			+ "/src/test/resources/installability/server/";

	@Before
	public void setUp() {
		sut = new AverageInstallationTimeCalculator();
	}

	@Test
	public void testNonArchive() {
		List<ReportEntry> result = sut.analyzeFile(Paths.get(resourcePaths
				+ "garbage.txt"));
		assertEquals(0, result.size());
	}

}
