package pete.metrics.adaptability;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import pete.metrics.adaptability.nodecounters.NodeCounter;
import pete.metrics.adaptability.nodecounters.SimpleNodeCounter;

public class SimpleNodeCounterTests {

	private NodeCounter simpleCounter;

	@Before
	public void setUp() {
		simpleCounter = new SimpleNodeCounter();
	}

	@Test
	public void testNodeCount() {
		Document testFile = getDom("src/test/resources/adaptability/ElementNumbers.bpmn");
		simpleCounter.addToCounts(testFile);
		int numberOfElements = simpleCounter.getElementNumbers().keySet()
				.size();
		assertEquals(2, numberOfElements);
	}

	private Document getDom(String file) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		DocumentBuilder db;
		Document dom = null;

		try (FileInputStream fis = new FileInputStream(new File(file))) {
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

			dom = db.parse(fis);

		} catch (ParserConfigurationException | SAXException | IOException e) {

		}
		return dom;
	}

}
