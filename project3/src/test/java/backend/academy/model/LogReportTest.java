package backend.academy.model;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class LogReportTest {

    @Test
    public void testLogReportBuilderAndGetters() {
        // Arrange
        LocalDateTime startDate = LocalDateTime.parse("2024-08-31T00:00:00");
        LocalDateTime endDate = LocalDateTime.parse("2024-08-31T23:59:59");

        // Act
        LogReport report = LogReport.builder()
            .files(new String[]{"access.log"})
            .startDate(startDate)
            .endDate(endDate)
            .totalRequests(10000)
            .averageResponseSize(500)
            .percentile95ResponseSize(950)
            .topResources(Map.of("/index.html", 5000L))
            .statusCodes(Map.of(200, 8000L))
            .requestMethods(Map.of("GET", 9000L))
            .topClients(Map.of("192.168.0.1", 500L))
            .build();

        // Assert
        assertThat(report.getFiles()).containsExactly("access.log");
        assertThat(report.getStartDate()).isEqualTo(startDate);
        assertThat(report.getEndDate()).isEqualTo(endDate);
        assertThat(report.getTotalRequests()).isEqualTo(10000);
        assertThat(report.getAverageResponseSize()).isEqualTo(500.0);
        assertThat(report.getPercentile95ResponseSize()).isEqualTo(950.0);
        assertThat(report.getTopResources()).containsEntry("/index.html", 5000L);
        assertThat(report.getStatusCodes()).containsEntry(200, 8000L);
        assertThat(report.getRequestMethods()).containsEntry("GET", 9000L);
        assertThat(report.getTopClients()).containsEntry("192.168.0.1", 500L);
    }
}
