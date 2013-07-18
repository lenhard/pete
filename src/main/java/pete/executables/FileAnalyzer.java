package pete.executables;

import java.nio.file.Path;
import java.util.List;

import pete.reporting.ReportEntry;

public interface FileAnalyzer {

	List<ReportEntry> analyzeFile(Path filePath);

}
