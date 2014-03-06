package pete.metrics.installability.deployability;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
import pete.metrics.installability.util.GroupReader;
import pete.reporting.ReportEntry;

public class DeploymentPackageAnalyzer implements FileAnalyzer {

	private HashSet<String> whiteList;

	private int effortOfPackageConstruction;

	private int descriptorSize;

	private int numberOfServices;

	private String tempDir;

	private List<Path> descriptorPaths;

	public DeploymentPackageAnalyzer() {
		whiteList = new HashSet<String>();
		whiteList.add(".componentType");
		whiteList.add("composite.xml");
		whiteList.add("catalog.xml");
		whiteList.add("composite.xml");
		whiteList.add(".pdd");
		whiteList.add(".MF");
		whiteList.add("deploy.xml");
		whiteList.add("jbi.xml");
		whiteList.add(".jar");
		whiteList.add(".zip");

		reset();
		tempDir = "tmp" + File.separator + System.currentTimeMillis();
	}

	@Override
	public List<ReportEntry> analyzeFile(Path filePath) {

		cleanUpTempFile(filePath);

		if (isArchive(filePath)) {

			inspectArchive(filePath);

			addDirectoryNumber();

			cleanUpTempDir(filePath);

			return buildResult(filePath);
		} else {
			return new ArrayList<>(0);
		}

	}

	private List<ReportEntry> buildResult(Path filePath) {
		List<ReportEntry> entryList = new ArrayList<>(1);
		ReportEntry entry = new ReportEntry(filePath.toString());
		entry.addVariable("group",
				GroupReader.readGroupFromPath(filePath.toString()));
		entry.addVariable("DE", (effortOfPackageConstruction + descriptorSize)
				+ "");
		entry.addVariable("EPC", effortOfPackageConstruction + "");
		entry.addVariable("DDS", descriptorSize + "");
		entry.addVariable("NServ", numberOfServices + "");
		entryList.add(entry);
		reset();
		return entryList;
	}

	private void addDirectoryNumber() {
		Set<Path> uniquePaths = new LinkedHashSet<>();
		for (Path path : descriptorPaths) {
			Path current = path.getParent();
			boolean isTopLevel = current.getNameCount() <= 3;
			while (!isTopLevel) {
				uniquePaths.add(current);
				current = current.getParent();
				isTopLevel = current.getNameCount() <= 3;
			}
		}
		// Count each dir that needs to be constructed for a descriptor
		effortOfPackageConstruction += uniquePaths.size();
	}

	private void cleanUpTempFile(Path filePath) {
		try {
			FileUtils.deleteDirectory(new File(tempDir + "/"
					+ filePath.getFileName()));
		} catch (IOException e) {
			System.err.println("Error while deleting file for analysis of "
					+ filePath.getFileName());
			e.printStackTrace();
		}
	}

	private void cleanUpTempDir(Path filePath) {
		try {
			FileUtils.deleteDirectory(new File(tempDir));
		} catch (IOException e) {
			System.err.println("Error while deleting file for analysis of "
					+ filePath.getFileName());
			e.printStackTrace();
		}
	}

	private boolean isArchive(Path filePath) {
		String fileName = filePath.toString();
		boolean isZip = fileName.endsWith(".zip");
		boolean isBpr = fileName.endsWith(".bpr");
		boolean isJar = fileName.endsWith(".jar");
		boolean isWar = fileName.endsWith(".war");
		boolean isEar = fileName.endsWith(".ear");
		return isZip || isBpr || isJar || isWar || isEar;
	}

	private void inspectArchive(Path filePath) {
		System.out.println("Analyzing " + filePath.toAbsolutePath().toString()
				+ " for deployment effort");

		try {
			unzipFileToTempDir(filePath);
			// COUNT: Archive Building, cost: 1
			effortOfPackageConstruction++;
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
				} else if (isWsdl(pathInZip)) {
					numberOfServices++;
				} else if (Files.isRegularFile(pathInZip)
						&& isRelevant(pathInZip)) {
					processRegularFile(pathInZip);
				}
			}
		}
	}

	private boolean isWsdl(Path path) {
		return path.toString().endsWith(".wsdl");
	}

	private void processRegularFile(Path pathInZip) {
		if (isArchive(pathInZip)) {
			inspectArchive(pathInZip);
		} else {
			// COUNT: Construct file. Cost: 1
			effortOfPackageConstruction++;
			descriptorPaths.add(pathInZip);
			int cost = checkXmlFile(pathInZip);
			if (cost == -1) {
				cost = checkTextFile(pathInZip);
			}

			if (cost > 0) {
				descriptorSize += cost;
			}
		}
	}

	private boolean isRelevant(Path path) {
		for (String item : whiteList) {
			if (path.toString().endsWith(item)) {
				return true;
			}
		}
		return false;
	}

	private int checkXmlFile(Path pathInZip) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;

		try (FileInputStream fis = new FileInputStream(pathInZip.toFile())) {
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

			Document dom = db.parse(fis);
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
					boolean isEmpty = attribute.getNodeValue().equals("");
					boolean isNamespace = isNamespaceDeclaration(attribute);
					if (!isEmpty && !isNamespace) {
						// COUNT: An attribute that contains something, cost:1
						sum++;
					}
				}
			}

			// COUNT: Non-empty text node
			if (isNonEmptyTextNode(node)) {
				sum++;
			}
		}
		return sum;
	}

	private boolean isNonEmptyTextNode(Node node) {
		boolean isTextNode = node.getNodeType() == Node.TEXT_NODE;
		String nodeValue = node.getNodeValue();
		boolean isNonEmpty = !(nodeValue == null)
				&& !("".equals(nodeValue.trim()));
		return isTextNode && isNonEmpty;
	}

	private boolean isNamespaceDeclaration(Node attribute) {
		return attribute.toString().startsWith("xmlns");
	}

	private int checkTextFile(Path pathInZip) {
		int sum = 0;
		try {
			for (String line : Files.readAllLines(pathInZip,
					Charset.defaultCharset())) {
				boolean isEmptyLine = line.trim().length() == 0;
				boolean isCommentLine = line.startsWith("#");
				if (!isEmptyLine && !isCommentLine) {
					// Count non-empty, non-comment line, cost: 1
					sum++;
				}
			}
		} catch (IOException e) {
			// On error: not readable, just quit
			return -1;
		}

		return sum;
	}

	private void reset() {
		effortOfPackageConstruction = 0;
		descriptorSize = 0;
		numberOfServices = 0;
		descriptorPaths = new LinkedList<Path>();
	}

	@Override
	public void traversalCompleted() {
		// Not needed here
	}
}
