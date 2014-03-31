package pete.metrics.adaptability.nodecounters;

import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.w3c.dom.Document;

public interface NodeCounter {

	void addToCounts(Document document);

	void writeToCsv(Path file);

	Map<String, AtomicInteger> getElementNumbers();

}
