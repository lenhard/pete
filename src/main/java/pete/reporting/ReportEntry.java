package pete.reporting;

import java.util.HashMap;

public class ReportEntry {

	private final String fileName;

	private HashMap<String, Integer> variables;

	public ReportEntry(String fileName) {
		this.fileName = fileName;
		variables = new HashMap<String, Integer>();
	}

	public void addVariable(String name, Integer value) {
		variables.put(name, value);
	}

	public String getFileName() {
		return fileName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(fileName + "\t");
		for (String variable : variables.keySet()) {
			builder.append(variable + ":" + variables.get(variable));
		}
		return builder.toString();
	}
}
