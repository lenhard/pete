package pete.executables;

import java.nio.file.Files;
import java.nio.file.Path;

import pete.metrics.installability.deployability.DeploymentPackageAnalyzer;
import pete.metrics.installability.server.AverageInstallationTimeCalculator;
import pete.metrics.portability.PortabilityAnalyzer;
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

		if (AnalysisType.DEPLOYABILITY.equals(type)) {
			fileAnalyzer = new DeploymentPackageAnalyzer();
		} else if (AnalysisType.INSTALLABILITY.equals(type)) {
			fileAnalyzer = new AverageInstallationTimeCalculator();
		} else if (AnalysisType.PORTABILITY.equals(type)) {
			fileAnalyzer = new PortabilityAnalyzer();
		} else if (AnalysisType.UNKNOWN.equals(type)) {
			throw new AnalysisException("no valid AnalysisType found");
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
