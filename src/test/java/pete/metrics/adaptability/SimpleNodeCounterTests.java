package pete.metrics.adaptability;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

import pete.metrics.adaptability.nodecounters.NodeCounter;
import pete.metrics.adaptability.nodecounters.SimpleNodeCounter;

public class SimpleNodeCounterTests {

	private NodeCounter simpleCounter;

	private AdaptabilityAnalyzer util = new AdaptabilityAnalyzer();

	@Before
	public void setUp() {
		simpleCounter = new SimpleNodeCounter();
	}

	@Test
	public void testNodeCount() {
		Document testFile = util
				.getDom("src/test/resources/adaptability/ElementNumbers.bpmn");
		simpleCounter.addToCounts(testFile);
		int numberOfElements = simpleCounter.getElementNumbers().keySet()
				.size();
		assertEquals(2, numberOfElements);
	}

}
