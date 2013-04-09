package pete.executables;

import java.nio.file.Path;

import pete.report.Report;

public interface FileAnalyzer {

	Report analyzeFile(Path filePath);

}
