package pete;

import java.nio.file.Path;
import java.nio.file.Paths;

import pete.executables.AnalysisWorkflow;

public class Pete {

	public static void main(String[] args) {
		System.out.println("Hello, I'm Pete");
		validateAndSetArgs(args);

		Path root = Paths.get(args[0]);

		AnalysisWorkflow workflow = new AnalysisWorkflow(root);

		workflow.start();
	}

	private static void validateAndSetArgs(String[] args) {
		if (args.length != 1) {
			System.out.println("Error: Wrong arguments!");
			System.out.println("Arguments must be:");
			System.out.println("[1]: Path to file or directory");
			System.exit(1);
		}
	}
}