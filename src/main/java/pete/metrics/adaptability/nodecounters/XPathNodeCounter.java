package pete.metrics.adaptability.nodecounters;

import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.w3c.dom.Node;

public class XPathNodeCounter implements NodeCounter {

	@Override
	public void addToCounts(Node node) {

	}

	@Override
	public void writeToCsv(Path file) {

	}

	@Override
	public Map<String, AtomicInteger> getElementNumbers() {
		return null;
	}

}
