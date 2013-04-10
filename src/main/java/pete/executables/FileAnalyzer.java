package pete.executables;

import java.nio.file.Path;

import pete.reporting.ReportEntry;

public interface FileAnalyzer {

	ReportEntry analyzeFile(Path filePath);

}
