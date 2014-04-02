package pete.executables;

import java.nio.file.Paths;

import org.junit.Test;

import pete.executables.AnalysisType;
import pete.executables.AnalysisWorkflow;

public class AnalysisWorkflowTests {

	@Test(expected = IllegalArgumentException.class)
	public void testNonExistingDirectory() {
		@SuppressWarnings("unused")
		AnalysisWorkflow sut = new AnalysisWorkflow(Paths.get("foobar"),
				AnalysisType.DEPLOYABILITY);
	}

}
