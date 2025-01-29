package backend.academy.parser;

import static org.assertj.core.api.Assertions.*;

import backend.academy.model.LogRecord;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class LogParserTest {

    @Test
    public void testParse_ValidLogLine() {
        // Arrange
        String logLine = "127.0.0.1 - john [31/Aug/2024:10:00:00 +0000] \"GET /index.html HTTP/1.1\" 200 1234 \"-\" \"Mozilla/5.0\"";
        LogParser parser = new LogParser();

        // Act
        LogRecord record = parser.parse(logLine);

        // Assert
        assertThat(record).isNotNull();
        assertThat(record.getRemoteAddr()).isEqualTo("127.0.0.1");
        assertThat(record.getRemoteUser()).isEqualTo("john");
        assertThat(record.getTimeLocal()).isEqualTo(LocalDateTime.parse("2024-08-31T10:00:00"));
        assertThat(record.getMethod()).isEqualTo("GET");
        assertThat(record.getResource()).isEqualTo("/index.html");
        assertThat(record.getProtocol()).isEqualTo("HTTP/1.1");
        assertThat(record.getStatus()).isEqualTo(200);
        assertThat(record.getBodyBytesSent()).isEqualTo(1234);
        assertThat(record.getHttpReferer()).isEqualTo("-");
        assertThat(record.getHttpUserAgent()).isEqualTo("Mozilla/5.0");
    }

    @Test
    public void testParse_InvalidLogLine_ShouldReturnNull() {
        // Arrange
        String logLine = "Invalid log line";
        LogParser parser = new LogParser();

        // Act
        LogRecord record = parser.parse(logLine);

        // Assert
        assertThat(record).isNull();
    }

    @Test
    public void testParse_LogLineWithBOM() {
        // Arrange
        String logLine = "\uFEFF127.0.0.1 - - [31/Aug/2024:10:00:00 +0000] \"GET / HTTP/1.1\" 200 1234 \"-\" \"Mozilla/5.0\"";
        LogParser parser = new LogParser();

        // Act
        LogRecord record = parser.parse(logLine);

        // Assert
        assertThat(record).isNotNull();
        assertThat(record.getRemoteAddr()).isEqualTo("127.0.0.1");
    }
}

