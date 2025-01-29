package backend.academy.reader;

import static org.assertj.core.api.Assertions.*;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;
import org.junit.jupiter.api.Test;

public class LogReaderFactoryTest {

    @Test
    public void testCreate_WithFilePaths_ReturnsFileLogReader() {
        // Arrange
        List<String> paths = List.of("logs/*.log");

        // Act
        LogReader reader = LogReaderFactory.create(paths, Charset.defaultCharset());

        // Assert
        assertThat(reader).isInstanceOf(FileLogReader.class);
    }

    @Test
    public void testCreate_WithValidUrl_ReturnsUrlLogReader() {
        // Arrange
        String url = "http://example.com/logs/access.log";
        List<String> paths = List.of(url);

        // Act
        LogReader reader = LogReaderFactory.create(paths, Charset.defaultCharset());

        // Assert
        assertThat(reader).isInstanceOf(UrlLogReader.class);
    }

    @Test
    public void testCreate_WithInvalidUrl_ReturnsFileLogReader() {
        // Arrange
        String url = "invalid_url";
        List<String> paths = List.of(url);

        // Act
        LogReader reader = LogReaderFactory.create(paths, Charset.defaultCharset());

        // Assert
        assertThat(reader).isInstanceOf(FileLogReader.class);
    }
}
