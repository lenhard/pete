package pete.reporting;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class ReportEntry {

	private final String fileName;

	private final ConcurrentHashMap<String, String> variables;

	public ReportEntry(String fileName) {
		this.fileName = fileName;
		variables = new ConcurrentHashMap<>();
	}

	public void addVariable(String name, String value) {
		variables.put(name, value);
	}

	public String getFileName() {
		return fileName;
	}

	public String getVariableValue(String name) {
		String result = variables.get(name);
		if (result == null) {
			return "";
		} else {
			return result;
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(fileName + "\t");

		variables.keySet().forEach(
				variable -> builder.append(variable + ":"
						+ variables.get(variable)));

		return builder.toString();
	}

	public String toStringWithSeparator(String separator) {
		StringBuilder builder = new StringBuilder(fileName);

		variables.keySet()
				.forEach(
						variable -> builder.append(separator
								+ variables.get(variable)));

		return builder.toString();
	}

	public Set<String> getVariableNames() {
		return variables.keySet();
	}
}
