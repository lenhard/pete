package pete.metrics.installability;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import pete.executables.FileAnalyzer;
import pete.report.Report;

public class DeploymentPackageAnalyzer implements FileAnalyzer {

	private HashSet<String> ignores;

	private int packageComplexity;

	public DeploymentPackageAnalyzer() {
		ignores = new HashSet<String>();
		ignores.add(".wsdl");
		ignores.add(".bpel");
		packageComplexity = 0;
	}

	@Override
	public Report analyzeFile(Path filePath) {
		Report report = new Report();

		if (isArchive(filePath)) {
			inspectArchive(filePath);
		}

		return report;
	}

	private boolean isArchive(Path filePath) {
		return filePath.endsWith(".zip") || filePath.endsWith(".bpr");
	}

	private void inspectArchive(Path filePath) {
		// TODO: rather: unzip in tempdir and inspect
		try (ZipFile zip = new ZipFile(filePath.toFile())) {
			@SuppressWarnings("unchecked")
			Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) zip
					.entries();
			while (entries.hasMoreElements()) {
				processEntry(entries.nextElement(), zip);
			}
		} catch (IOException e) {
			// file not processable for some reason -> ignore
			System.err.println("Exception while inspecting "
					+ filePath.toAbsolutePath() + ": " + e.getMessage());
		}
	}

	private void processEntry(ZipEntry entry, ZipFile zip) {
		if (isIgnored(entry)) {
			return;
		} else if (entry.isDirectory()) {
			packageComplexity++;
		} else {
			inspectFile(entry, zip);
		}
	}

	private boolean isIgnored(ZipEntry entry) {
		for (String ignore : ignores) {
			if (entry.getName().endsWith(ignore)) {
				return true;
			}
		}
		return false;
	}

	private void inspectFile(ZipEntry entry, ZipFile zip) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
			Document dom = db.parse(zip.getInputStream(entry));
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}

	}
}
