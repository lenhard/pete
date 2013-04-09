package pete.executables;

import java.nio.file.Files;
import java.nio.file.Path;

import pete.metrics.installability.DeploymentPackageAnalyzer;

public class AnalysisWorkflow {

	private Path root;

	private DirectoryAnalyzer dirAnalyzer;

	private FileAnalyzer fileAnalyzer;

	public AnalysisWorkflow(Path root) {
		this.root = root;
		fileAnalyzer = new DeploymentPackageAnalyzer();
		dirAnalyzer = new DirectoryAnalyzer(fileAnalyzer);
	}

	public void start() {
		if (!Files.isDirectory(root)) {
			parseFile(root);
		} else {
			parseDirectory(root);
		}
		writeResults();
	}

	private void parseFile(Path file) {
		fileAnalyzer.analyzeFile(file);
	}

	private void parseDirectory(Path root) {
		dirAnalyzer.analyzeDirectory(root);
	}

	private void writeResults() {
		// TODO Auto-generated method stub

	}

}
