package pete;

import java.nio.file.Path;
import java.nio.file.Paths;

import pete.executables.AnalysisWorkflow;

public class Pete {

	public static void main(String[] args) {
		System.out.println("Hello, I'm Pete");
		validateArgs(args);

		Path root = Paths.get(args[0]);

		AnalysisWorkflow workflow = new AnalysisWorkflow(root);

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
		}
	}
}