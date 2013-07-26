package pete.executables;

import java.nio.file.Files;
import java.nio.file.Path;

import pete.metrics.installability.deployability.DeploymentPackageAnalyzer;
import pete.metrics.installability.server.AverageInstallationTimeCalculator;
import pete.reporting.Report;
import pete.reporting.ReportEntry;
import pete.reporting.ReportWriter;

public class AnalysisWorkflow {

	private Path root;

	private DirectoryAnalyzer dirAnalyzer;

	private FileAnalyzer fileAnalyzer;

	private Report report;

	public AnalysisWorkflow(Path root, AnalysisType type) {
		this.root = root;

		if (!Files.exists(root)) {
			throw new IllegalArgumentException("path " + root.toString()
					+ " does not exist");
		}

		if (type.equals(AnalysisType.DEPLOYABILITY)) {
			fileAnalyzer = new DeploymentPackageAnalyzer();
		} else {
			fileAnalyzer = new AverageInstallationTimeCalculator();
		}
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
		for (ReportEntry entry : fileAnalyzer.analyzeFile(file)) {
			report.addEntry(entry);
		}
	}

	private void parseDirectory(Path root) {
		report = dirAnalyzer.analyzeDirectory(root);
	}

	private void writeResults() {
		ReportWriter writer = new ReportWriter(report);
		writer.writeToExcelFile("results.csv");
		writer.writeToRFile("r-results.csv");
	}
}
