package backend.academy.reader;

import java.util.stream.Stream;

public interface LogReader extends AutoCloseable {
    Stream<String> readLines();
}
