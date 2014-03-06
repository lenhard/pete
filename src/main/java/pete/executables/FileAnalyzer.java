package pete.executables;

import java.nio.file.Path;
import java.util.List;

import pete.reporting.ReportEntry;

public interface FileAnalyzer {

	/**
	 * Analyses a file and returns zero or more ReportEntries, depending on the
	 * result of the analysis
	 * 
	 * @param filePath
	 *            the path to the file to be analysed
	 * @return a, possibly empty, List of ReportEntries that describe the result
	 *         of the analysis
	 */
	List<ReportEntry> analyzeFile(Path filePath);

	/**
	 * Notifies the FileAnalyzer that the last file analysis has occured. Can be
	 * used to perform cleanup operations
	 */
	void traversalCompleted();

}
