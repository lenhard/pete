package pete.reporting;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ReportWriter {

	private Report report;

	private String outputFile;

	public ReportWriter(Report report, String filePath) {
		this.report = report;
		this.outputFile = filePath;
	}

	public void writeOut() {
		printToConsole();
		printToFile();
	}

	private void printToConsole() {
		for (ReportEntry entry : report) {
			System.out.println(entry);
		}
	}

	private void printToFile() {
		try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
			for (ReportEntry entry : report) {
				writer.println(entry.toStringWithSeparator(";"));
			}
		} catch (IOException e) {
			System.err.println("Could no write to file " + outputFile + ": "
					+ e.getMessage());
			e.printStackTrace();
		}
	}
}
