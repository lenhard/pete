package pete;

import java.io.IOException;

import pete.metrics.installability.server.AverageInstallationTimeCalculator;

public class InstallationTimeCalculator {

	public static void main(String[] args) throws IOException {
		System.out.println("Starting calculation...");
		new AverageInstallationTimeCalculator().scanLog(args);
		System.out.println("Done!");
	}

}
