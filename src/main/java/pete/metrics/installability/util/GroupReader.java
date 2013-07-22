package pete.metrics.installability.util;

import java.nio.file.Path;

public class GroupReader {

	public static String readGroupFromPath(String pathName) {
		if (pathName.contains("active-bpel") || pathName.contains("ActiveBPEL")) {
			return "active";
		} else if (pathName.contains("bpelg") || pathName.contains("bpel-g")) {
			return "bpelg";
		} else if (pathName.contains("ode") || pathName.contains("ODE")) {
			return "ode";
		} else if (pathName.contains("openesb23")
				|| pathName.toLowerCase().contains("openesb")) {
			return "openesb";
		} else if (pathName.contains("orchestra")) {
			return "orchestra";
		} else if (pathName.contains("petals")) {
			return "petals";
		} else {
			return "";
		}
	}

	public static String readGroupFromPath(Path path) {
		return readGroupFromPath(path.toString());
	}

}
