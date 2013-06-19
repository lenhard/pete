package pete;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Scanner;

public class InstallationTimeCalculator {

	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(Paths.get(args[0]));
		PrintWriter writer = new PrintWriter(new FileOutputStream("out.csv"));
		writer.println("engine;time;" + addAverages());
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (line.contains("/install (")) {

				String[] split = line.split("\\(");
				double min = Double.parseDouble(split[1].substring(0, 4));

				split = line.split("/");
				String engineName = split[1];
				writer.println(engineName + ";" + String.format("%1$,2f", min));
			} else if (line
					.contains("SOAP BC Installation failed - shutdown, reinstall and start petalsesb again")) {
				writer.println("petals;;" + 1);
			}
		}
		writer.close();
		scanner.close();
	}

	private static String addAverages() {
		String ode = "=MITTELWERT(B2:B152)";
		String bpelg = "=MITTELWERT(B153:B303)";
		String orchestra = "=MITTELWERT(B304:B454)";
		String openesb23 = "=MITTELWERT(B455:B605)";
		String petals = "=MITTELWERT(B606:B756)";
		String active = "=MITTELWERT(B757:B907)";
		String delimiter = ";";
		return ode + delimiter + bpelg + delimiter + orchestra + delimiter
				+ openesb23 + delimiter + petals + delimiter + active;
	}
}
