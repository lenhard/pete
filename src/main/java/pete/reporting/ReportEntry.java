package pete.reporting;

import java.util.HashMap;
import java.util.Set;

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

	public int getVariableValue(String name) {
		Integer result = variables.get(name);
		if (result == null) {
			return 0;
		} else {
			return result;
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(fileName + "\t");
		for (String variable : variables.keySet()) {
			builder.append(variable + ":" + variables.get(variable));
		}
		return builder.toString();
	}

	public String toStringWithSeparator(String separator) {
		StringBuilder builder = new StringBuilder(fileName);
		for (String variable : variables.keySet()) {
			builder.append(separator + variables.get(variable));
		}
		return builder.toString();
	}

	public Set<String> getVariableNames() {
		return variables.keySet();
	}
}
