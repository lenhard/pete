package pete;

import java.nio.file.Path;
import java.nio.file.Paths;

import pete.executables.AnalysisType;
import pete.executables.AnalysisWorkflow;

public class Pete {

	public static void main(String[] args) {
		System.out.println("Hello, I'm Pete");
		validateArgs(args);

		Path root = Paths.get(args[1]);

		AnalysisWorkflow workflow = new AnalysisWorkflow(root,
				getAnalysisType(args[0]));

		workflow.start();
	}

	private static void validateArgs(String[] args) {
		if (args.length != 2) {
			System.out.println("Error: Wrong arguments!");
			System.out.println("Arguments must be:");
			System.out
					.println("[1]: Selection of analysis type. Use -d for deployment anlysis, -i for installability analysis");
			System.out.println("[2]: Path to file or directory");
			System.exit(1);
		} else if (!args[0].equals("-i") && !args[0].equals("-d")) {
			System.out.println("Unknown option: " + args[0]);
			System.out.println("Available options: -i | -d");
			System.exit(1);
		}
	}

	private static AnalysisType getAnalysisType(String option) {
		if ("-i".equals(option)) {
			return AnalysisType.INSTALLABILITY;
		} else {
			return AnalysisType.DEPLOYABILITY;
		}
	}
}