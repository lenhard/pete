package pete.executables;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

import pete.reporting.Report;

public class DirectoryAnalyzer {

	private FileAnalyzer fileAnalyzer;

	public DirectoryAnalyzer(FileAnalyzer fileAnalyzer) {
		this.fileAnalyzer = fileAnalyzer;
	}

	public Report analyzeDirectory(Path directory) {
		Report report = new Report();

		try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
			for (Path path : stream) {
				if (Files.isRegularFile(path)) {
					report.append(fileAnalyzer.analyzeFile(path));
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
