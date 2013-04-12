package pete.reporting;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Report implements Iterable<ReportEntry> {

	private List<ReportEntry> entries;

	public Report() {
		entries = new LinkedList<>();
	}

	public void addEntry(ReportEntry entry) {
		entries.add(entry);
	}

	public void append(Report report) {
		entries.addAll(report.getEntries());
	}

	public List<ReportEntry> getEntries() {
		return Collections.unmodifiableList(entries);
	}

	@Override
	public Iterator<ReportEntry> iterator() {
		return Collections.unmodifiableList(entries).iterator();
	}

	public int getSummedVariable(String name) {
		int sum = 0;
		for (ReportEntry entry : entries) {
			sum += entry.getVariableValue(name);
		}
		return sum;
	}

}
