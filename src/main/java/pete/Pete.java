package pete;

import java.nio.file.Path;
import java.nio.file.Paths;

import pete.executables.AnalysisType;
import pete.executables.AnalysisWorkflow;

public class Pete {

	public static void main(String[] args) {
		printGreeting();

		validateArgs(args);

		Path root = Paths.get(args[1]);

		AnalysisWorkflow workflow = new AnalysisWorkflow(root,
				getAnalysisType(args[0]));

		workflow.start();
	}

	private static void printGreeting() {
		System.out.println("Hello, I'm Pete");
		System.out.println("I analyse code and compute portability metrics");
	}

	private static void validateArgs(String[] args) {
		if (args.length != 2) {
			System.out.println("Error: Wrong arguments!");
			System.out.println("Arguments must be:");
			System.out
					.println("[1]: Selection of analysis type. Use -p for analysing direct portability, -d for deployment analysis, -i for installability analysis, -a for adaptability analysis");
			System.out.println("[2]: Path to file or directory");
			System.exit(1);
		} else if (!args[0].equals("-i") && !args[0].equals("-d")
				&& !args[0].equals("-p") && !args[0].equals("-a")) {
			System.out.println("Unknown option: " + args[0]);
			System.out.println("Available options: -i | -d | -p | -a");
			System.exit(1);
		}
	}

	private static AnalysisType getAnalysisType(String option) {
		if ("-i".equals(option)) {
			return AnalysisType.INSTALLABILITY;
		} else if ("-d".equals(option)) {
			return AnalysisType.DEPLOYABILITY;
		} else if ("-p".equals(option)) {
			return AnalysisType.PORTABILITY;
		} else if ("-a".equals(option)) {
			return AnalysisType.ADAPTABILITY;
		} else {
			return AnalysisType.UNKNOWN;
		}
	}
}