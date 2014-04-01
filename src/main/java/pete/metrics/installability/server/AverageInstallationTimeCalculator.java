package pete.metrics.installability.server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import pete.executables.FileAnalyzer;
import pete.reporting.ReportEntry;

public class AverageInstallationTimeCalculator implements FileAnalyzer {

	private static final String RAW_FILE = "raw.csv";

	private static final String ACTIVE_BPEL_NAME = "active-bpel";

	private static final String ORCHESTRA_NAME = "orchestra";

	private static final String PETALS_NAME = "petalsesb41";

	private static final String OPENESB_NAME = "openesb23";

	private static final String BPELG_NAME = "bpelg";

	private static final String ODE_NAME = "ode";

	private HashMap<String, List<Integer>> entries;

	private HashMap<String, AtomicInteger> failures;

	private HashMap<String, Integer> installationSteps;

	private String fileName;

	List<String> fullLog;

	public AverageInstallationTimeCalculator() {
		reset();
		installationSteps = new HashMap<>();
		installationSteps.put(ACTIVE_BPEL_NAME, new Integer(6));
		installationSteps.put(ORCHESTRA_NAME, new Integer(7));
		installationSteps.put(PETALS_NAME, new Integer(5));
		installationSteps.put(OPENESB_NAME, new Integer(7));
		installationSteps.put(BPELG_NAME, new Integer(6));
		installationSteps.put(ODE_NAME, new Integer(6));
		fullLog = new LinkedList<>();
		fullLog.add("engine,time,failed");
	}

	private void reset() {
		entries = new HashMap<>();
		entries.put(ODE_NAME, new ArrayList<Integer>(153));
		entries.put(BPELG_NAME, new ArrayList<Integer>(153));
		entries.put(OPENESB_NAME, new ArrayList<Integer>(153));
		entries.put(PETALS_NAME, new ArrayList<Integer>(153));
		entries.put(ORCHESTRA_NAME, new ArrayList<Integer>(153));
		entries.put(ACTIVE_BPEL_NAME, new ArrayList<Integer>(153));
		failures = new HashMap<>();
		failures.put(ODE_NAME, new AtomicInteger(0));
		failures.put(BPELG_NAME, new AtomicInteger(0));
		failures.put(OPENESB_NAME, new AtomicInteger(0));
		failures.put(PETALS_NAME, new AtomicInteger(0));
		failures.put(ORCHESTRA_NAME, new AtomicInteger(0));
		failures.put(ACTIVE_BPEL_NAME, new AtomicInteger(0));
		fileName = "";
	}

	private void scanLog(Path filePath) throws IOException,
	FileNotFoundException {
		System.out.println("Analysing file " + filePath.toString()
				+ " for installability");
		Scanner scanner = new Scanner(filePath);
		fileName = filePath.toString();

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (line.contains("/install (")) {

				String[] split = line.split("/");
				String engineName = split[1];

				int sec = Integer.parseInt(split[3].substring(1,
						(split[3].length() - 5)));

				entries.get(engineName).add(sec);

				fullLog.add(engineName + "," + sec);

			} else if (line
					.contains("SOAP BC Installation failed - shutdown, reinstall and start petalsesb again")) {
				failures.get(PETALS_NAME).incrementAndGet();
			}
		}
		scanner.close();
	}

	private double getAverage(String engineName) {
		AtomicInteger sum = new AtomicInteger(0);
		entries.get(engineName).forEach(entry -> sum.addAndGet(entry));
		double avg = sum.doubleValue() / entries.get(engineName).size();
		return avg;
	}

	private String getESR(String engineName) {
		int numOfFailures = failures.get(engineName).get();
		int totalSuccesses = entries.get(engineName).size() + numOfFailures;
		double petalsESR = ((double) totalSuccesses)
				/ ((double) (numOfFailures + totalSuccesses));
		return String.format("%1$.2f", petalsESR);
	}

	private double getIE(String engineName) {
		return getAverage(engineName) / installationSteps.get(engineName);
	}

	@Override
	public List<ReportEntry> analyzeFile(Path filePath) {
		List<ReportEntry> result = null;
		if (Files.isRegularFile(filePath) && Files.isReadable(filePath)) {
			try {
				scanLog(filePath);
			} catch (IOException e) {
				result = new ArrayList<ReportEntry>(0);
			}

			result = buildResults();

		} else {
			result = new ArrayList<ReportEntry>(0);
		}

		reset();
		return result;
	}

	private List<ReportEntry> buildResults() {
		List<ReportEntry> results = new LinkedList<>();
		String formatString = "%1$,.2f";
		for (String engine : entries.keySet()) {
			if (entries.get(engine).size() > 0) {
				ReportEntry entry = new ReportEntry(fileName);
				entry.addVariable("engine", engine);
				entry.addVariable("ESR", getESR(engine));
				entry.addVariable("AIT",
						String.format(formatString, getAverage(engine)));
				entry.addVariable("IE",
						String.format(formatString, getIE(engine)));
				entry.addVariable("N", entries.get(engine).size() + "");
				results.add(entry);
			}
		}

		return results;
	}

	@Override
	public void traversalCompleted() {
		try {
			Files.write(Paths.get(RAW_FILE), fullLog, Charset.defaultCharset());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
