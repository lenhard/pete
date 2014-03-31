package pete.metrics.adaptability.nodecounters;

import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.w3c.dom.Node;

public interface NodeCounter {

	void addToCounts(Node node);

	void writeToCsv(Path file);

	Map<String, AtomicInteger> getElementNumbers();

}
