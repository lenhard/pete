package pete.metrics.installability.server;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import pete.executables.FileAnalyzer;
import pete.reporting.ReportEntry;

public class AverageInstallationTimeCalculator implements FileAnalyzer {

	private static final String ACTIVE_BPEL_NAME = "active-bpel";

	private static final String ORCHESTRA_NAME = "orchestra";

	private static final String PETALS_NAME = "petalsesb41";

	private static final String OPENESB_NAME = "openesb23";

	private static final String BPELG_NAME = "bpelg";

	private static final String ODE_NAME = "ode";

	private HashMap<String, List<Integer>> entries;

	private HashMap<String, AtomicInteger> failures;

	public AverageInstallationTimeCalculator() {
		entries = new HashMap<>();
		entries.put(ODE_NAME, new ArrayList<Integer>(153));
		entries.put(BPELG_NAME, new ArrayList<Integer>(153));
		entries.put(OPENESB_NAME, new ArrayList<Integer>(153));
		entries.put(PETALS_NAME, new ArrayList<Integer>(153));
		entries.put(ORCHESTRA_NAME, new ArrayList<Integer>(153));
		entries.put(ACTIVE_BPEL_NAME, new ArrayList<Integer>(153));
		failures = new HashMap<>();
		failures.put(PETALS_NAME, new AtomicInteger(0));
	}

	public void scanLog(String[] args) throws IOException,
			FileNotFoundException {
		Scanner scanner = new Scanner(Paths.get(args[0]));
		PrintWriter writer = new PrintWriter(new FileOutputStream("out.csv"));

		writer.println("engine;time;" + addAverages());
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (line.contains("/install (")) {

				String[] split = line.split("/");
				String engineName = split[1];

				int sec = Integer.parseInt(split[3].substring(1,
						(split[3].length() - 5)));

				entries.get(engineName).add(sec);

				writer.println(engineName + ";" + sec);

			} else if (line
					.contains("SOAP BC Installation failed - shutdown, reinstall and start petalsesb again")) {
				writer.println("petals;;" + 1);
				failures.get(PETALS_NAME).incrementAndGet();
			}
		}
		writer.println(buildAveragesString());
		writer.println(getESR());
		writer.close();
		scanner.close();
	}

	private String buildAveragesString() {
		StringBuilder title = new StringBuilder();
		StringBuilder body = new StringBuilder();

		for (String engine : entries.keySet()) {
			title.append(engine + ";");
			double avg = getAverage(engine);
			body.append(String.format("%1$,2f", avg) + ";");
		}
		title.append("\n" + body);
		return title.toString();
	}

	private double getAverage(String engineName) {
		int sum = 0;
		for (Integer entry : entries.get(engineName)) {
			sum += entry;
		}
		return (double) sum / (double) entries.get(engineName).size();
	}

	private String addAverages() {
		String ode = "=MITTELWERT(B2:B153)";
		String bpelg = "=MITTELWERT(B154:B305)";
		String orchestra = "=MITTELWERT(B306:B457)";
		String openesb23 = "=MITTELWERT(B458:B609)";
		String petals = "=MITTELWERT(B610:B756)";
		String active = "=MITTELWERT(B780:B931)";
		String delimiter = ";";
		return ode + delimiter + bpelg + delimiter + orchestra + delimiter
				+ openesb23 + delimiter + petals + delimiter + active;
	}

	private String getESR() {
		int numOfPetalsFailures = failures.get(PETALS_NAME).get();
		int totalPetalsAttempts = entries.get(PETALS_NAME).size()
				+ numOfPetalsFailures;
		double petalsESR = ((double) totalPetalsAttempts)
				/ ((double) (numOfPetalsFailures + totalPetalsAttempts));
		return "ESR;" + String.format("%1$,2f", petalsESR);
	}

	@Override
	public List<ReportEntry> analyzeFile(Path filePath) {
		// TODO Auto-generated method stub
		return null;
	}
}
