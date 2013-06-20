package pete.executables;

import java.nio.file.Files;
import java.nio.file.Path;

import pete.metrics.installability.application.DeploymentPackageAnalyzer;
import pete.reporting.Report;
import pete.reporting.ReportWriter;

public class AnalysisWorkflow {

	private Path root;

	private DirectoryAnalyzer dirAnalyzer;

	private FileAnalyzer fileAnalyzer;

	private Report report;

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
		report = new Report();
		report.addEntry(fileAnalyzer.analyzeFile(file));
	}

	private void parseDirectory(Path root) {
		report = dirAnalyzer.analyzeDirectory(root);
	}

	private void writeResults() {
		new ReportWriter(report, "results.csv").writeOut();
	}

}
