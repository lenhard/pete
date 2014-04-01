package pete.executables;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

import pete.reporting.Report;
import pete.reporting.ReportEntry;

public final class DirectoryAnalyzer {

	private final FileAnalyzer fileAnalyzer;

	private final Report report;

	public DirectoryAnalyzer(FileAnalyzer fileAnalyzer) {
		this.fileAnalyzer = fileAnalyzer;
		report = new Report();
	}

	public Report analyzeDirectory(Path directory) {

		try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
			for (Path path : stream) {
				if (Files.isRegularFile(path)) {
					for (ReportEntry entry : fileAnalyzer.analyzeFile(path)) {
						report.addEntry(entry);
					}
				} else if (Files.isDirectory(path)) {
					analyzeDirectory(path);
				}
			}
		} catch (IOException e) {
			throw new AnalysisException(e);
		}

		return report;
	}

}
