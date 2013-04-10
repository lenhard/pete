package pete.metrics.installability;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import pete.executables.FileAnalyzer;
import pete.reporting.ReportEntry;

public class DeploymentPackageAnalyzer implements FileAnalyzer {

	private HashSet<String> ignores;

	private int packageComplexity;

	private String tempDir;

	public DeploymentPackageAnalyzer() {
		ignores = new HashSet<String>();
		ignores.add(".wsdl");
		ignores.add(".bpel");
		packageComplexity = 0;
		tempDir = "tmp";
	}

	@Override
	public ReportEntry analyzeFile(Path filePath) {
		ReportEntry entry = new ReportEntry(filePath.toString());

		try {
			FileUtils.deleteDirectory(new File(tempDir + "/"
					+ filePath.getFileName()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (isArchive(filePath)) {
			inspectArchive(filePath);
		}

		entry.addVariable("packageComplexity", packageComplexity);
		return entry;
	}

	private boolean isArchive(Path filePath) {
		String fileName = filePath.toString();
		boolean isZip = fileName.endsWith(".zip");
		boolean isBpr = fileName.endsWith(".bpr");
		boolean isJar = fileName.endsWith(".jar");
		return isZip || isBpr || isJar;
	}

	private void inspectArchive(Path filePath) {
		try {
			unzipFileToTempDir(filePath);
			// COUNT: Archive Building, cost: 1
			packageComplexity++;
		} catch (ZipException e) {
			System.err.println("Could not extract archive: "
					+ filePath.toAbsolutePath() + " Ignoring file. Error: "
					+ e.getMessage());
			return;
		}

		try {
			processArchiveDirectory(Paths.get(tempDir + "/"
					+ filePath.getFileName()));
		} catch (IOException e) {
			System.err.println("Error while analyzing archive "
					+ filePath.toAbsolutePath() + ": " + e.getMessage()
					+ " Skipping analysis");
		}

	}

	private void unzipFileToTempDir(Path zipFile) throws ZipException {
		ZipFile zip = new ZipFile(zipFile.toString());
		zip.extractAll(tempDir + "/" + zipFile.getFileName());
	}

	private void processArchiveDirectory(Path dirPath) throws IOException {
		try (DirectoryStream<Path> dirStream = Files
				.newDirectoryStream(dirPath)) {
			for (Path pathInZip : dirStream) {
				if (Files.isDirectory(pathInZip)) {
					processArchiveDirectory(pathInZip);
					// COUNT: Constructing directory. Cost: 1
					packageComplexity++;
				} else if (Files.isRegularFile(pathInZip)) {
					processRegularFile(pathInZip);
				}
			}
		}
	}

	private void processRegularFile(Path pathInZip) {
		if (isIgnored(pathInZip)) {
			return;
		} else {
			// COUNT: Construct file. Cost: 1
			packageComplexity++;
			int cost = checkXmlFile(pathInZip);
			if (cost == -1) {
				cost = checkTextFile(pathInZip);
			}

			if (cost > 0) {
				packageComplexity += cost;
			}
		}
	}

	private boolean isIgnored(Path path) {
		for (String ignore : ignores) {
			if (path.toString().endsWith(ignore)) {
				return true;
			}
		}
		return false;
	}

	private int checkXmlFile(Path pathInZip) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
			db.setErrorHandler(new ErrorHandler() {

				@Override
				public void error(SAXParseException arg0) throws SAXException {
					throw arg0;
				}

				@Override
				public void fatalError(SAXParseException arg0)
						throws SAXException {
					throw arg0;
				}

				@Override
				public void warning(SAXParseException arg0) throws SAXException {

				}
			});
			Document dom = db.parse(new FileInputStream(pathInZip
					.toAbsolutePath().toFile()));
			// COUNT: root node of the document
			return countElementsAndAttributes(dom.getChildNodes());
		} catch (ParserConfigurationException | SAXException | IOException e) {
			return -1;
		}
	}

	private int countElementsAndAttributes(NodeList nodes) {
		int sum = 0;
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			// count attributes and elements
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				// COUNT: An element, cost:1
				sum++;
				if (node.getChildNodes().getLength() > 0) {
					sum += countElementsAndAttributes(node.getChildNodes());
				}

				NamedNodeMap attributes = node.getAttributes();
				for (int j = 0; j < attributes.getLength(); j++) {
					Node attribute = attributes.item(j);
					if (!attribute.getNodeValue().equals("")) {
						// COUNT: An attribute that contains something, cost:1
						sum++;
					}
				}
			}
		}
		return sum;
	}

	private int checkTextFileAlt(Path pathInZip) {
		int sum = 0;
		try (Scanner scanner = new Scanner(pathInZip)) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.trim().length() > 0) {
					// Count non-empty line, cost: 1
					sum++;
				}
			}
		} catch (IOException e) {
			// On error: not readable, just quit
			return -1;
		}
		return sum;
	}

	private int checkTextFile(Path pathInZip) {
		int sum = 0;
		try {
			for (String line : Files.readAllLines(pathInZip,
					Charset.defaultCharset())) {
				if (line.trim().length() > 0) {
					// Count non-empty line, cost: 1
					sum++;
				}
			}
		} catch (IOException e) {
			// On error: not readable, just quit
			return -1;
		}

		return sum;
	}
}
