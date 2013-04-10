package pete.executables;

import java.nio.file.Path;

import pete.reporting.Report;

public interface FileAnalyzer {

	Report analyzeFile(Path filePath);

}
