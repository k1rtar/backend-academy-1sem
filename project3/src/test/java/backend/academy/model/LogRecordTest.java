package backend.academy.model;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class LogRecordTest {

    @Test
    public void testLogRecordBuilderAndGetters() {
        // Arrange
        LocalDateTime time = LocalDateTime.parse("2024-08-31T12:00:00");

        // Act
        LogRecord logRecord = LogRecord.builder()
            .remoteAddr("127.0.0.1")
            .remoteUser("john")
            .timeLocal(time)
            .method("GET")
            .resource("/index.html")
            .protocol("HTTP/1.1")
            .status(200)
            .bodyBytesSent(1024)
            .httpReferer("-")
            .httpUserAgent("Mozilla/5.0")
            .build();

        // Assert
        assertThat(logRecord.getRemoteAddr()).isEqualTo("127.0.0.1");
        assertThat(logRecord.getRemoteUser()).isEqualTo("john");
        assertThat(logRecord.getTimeLocal()).isEqualTo(time);
        assertThat(logRecord.getMethod()).isEqualTo("GET");
        assertThat(logRecord.getResource()).isEqualTo("/index.html");
        assertThat(logRecord.getProtocol()).isEqualTo("HTTP/1.1");
        assertThat(logRecord.getStatus()).isEqualTo(200);
        assertThat(logRecord.getBodyBytesSent()).isEqualTo(1024);
        assertThat(logRecord.getHttpReferer()).isEqualTo("-");
        assertThat(logRecord.getHttpUserAgent()).isEqualTo("Mozilla/5.0");
    }
}

