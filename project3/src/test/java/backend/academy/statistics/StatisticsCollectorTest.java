
package backend.academy.statistics;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import backend.academy.config.Config;
import backend.academy.model.LogRecord;
import backend.academy.model.LogReport;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

public class StatisticsCollectorTest {

    @Test
    public void testCollect_WithValidRecords() {
        // Arrange
        StatisticsCollector collector = new StatisticsCollector();
        Config config = mock(Config.class);
        when(config.getPaths()).thenReturn(List.of("access.log"));
        when(config.getFrom()).thenReturn(LocalDateTime.parse("2024-08-31T00:00:00"));
        when(config.getTo()).thenReturn(LocalDateTime.parse("2024-08-31T23:59:59"));

        LogRecord record1 = LogRecord.builder()
            .remoteAddr("192.168.0.1")
            .timeLocal(LocalDateTime.parse("2024-08-31T10:00:00"))
            .method("GET")
            .resource("/index.html")
            .status(200)
            .bodyBytesSent(500)
            .build();

        LogRecord record2 = LogRecord.builder()
            .remoteAddr("192.168.0.2")
            .timeLocal(LocalDateTime.parse("2024-08-31T11:00:00"))
            .method("POST")
            .resource("/submit")
            .status(404)
            .bodyBytesSent(1000)
            .build();

        Stream<LogRecord> records = Stream.of(record1, record2);

        // Act
        LogReport report = collector.collect(records, config);

        // Assert
        assertThat(report.getTotalRequests()).isEqualTo(2);
        assertThat(report.getAverageResponseSize()).isEqualTo(750.0);
        assertThat(report.getPercentile95ResponseSize()).isEqualTo(1000.0);
        assertThat(report.getTopResources()).containsEntry("/index.html", 1L).containsEntry("/submit", 1L);
        assertThat(report.getStatusCodes()).containsEntry(200, 1L).containsEntry(404, 1L);
        assertThat(report.getRequestMethods()).containsEntry("GET", 1L).containsEntry("POST", 1L);
        assertThat(report.getTopClients()).containsEntry("192.168.0.1", 1L).containsEntry("192.168.0.2", 1L);
    }

    @Test
    public void testCollect_WithNoRecords() {
        // Arrange
        StatisticsCollector collector = new StatisticsCollector();
        Config config = mock(Config.class);
        when(config.getPaths()).thenReturn(List.of("access.log"));
        when(config.getFrom()).thenReturn(LocalDateTime.parse("2024-08-31T00:00:00"));
        when(config.getTo()).thenReturn(LocalDateTime.parse("2024-08-31T23:59:59"));

        Stream<LogRecord> records = Stream.empty();

        // Act
        LogReport report = collector.collect(records, config);

        // Assert
        assertThat(report.getTotalRequests()).isEqualTo(0);
        assertThat(report.getAverageResponseSize()).isEqualTo(0.0);
        assertThat(report.getPercentile95ResponseSize()).isEqualTo(0.0);
        assertThat(report.getTopResources()).isEmpty();
        assertThat(report.getStatusCodes()).isEmpty();
        assertThat(report.getRequestMethods()).isEmpty();
        assertThat(report.getTopClients()).isEmpty();
    }
}

