package pete.reporting;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public final class ReportWriter {

	private final Report report;

	private final String excelDelimiter = ";";

	private final String rDelimiter = ",";

	public ReportWriter(Report report) {
		this.report = report;
	}

	public void printToConsole() {
		for (ReportEntry entry : report) {
			System.out.println(entry);
		}
	}

	public void writeToExcelFile(String outputFile) {
		writeToFile(outputFile, excelDelimiter);
	}

	private void writeToFile(String outputFile, String delimiter) {
		try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
			printHeader(writer, delimiter);
			printBody(writer, delimiter);
		} catch (IOException e) {
			System.err.println("Could no write to file " + outputFile + ": "
					+ e.getMessage());
			e.printStackTrace();
		}
	}

	private void printHeader(PrintWriter writer, String delimiter) {
		writer.print("filename" + delimiter);
		for (String varName : report.getEntries().get(0).getVariableNames()) {
			writer.print(varName + delimiter);
		}
		writer.println();
	}

	private void printBody(PrintWriter writer, String delimiter) {
		for (ReportEntry entry : report) {
			writer.println(entry.toStringWithSeparator(delimiter));
		}
	}

	public void writeToRFile(String outputFile) {
		writeToFile(outputFile, rDelimiter);
	}
}
