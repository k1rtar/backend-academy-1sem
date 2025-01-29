package backend.academy.formatter;

import static org.assertj.core.api.Assertions.*;

import backend.academy.model.LogReport;
import java.time.LocalDateTime;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportFormatterTest {

    private LogReport report;

    @BeforeEach
    public void setUp() {
        report = LogReport.builder()
            .files(new String[]{"access.log"})
            .startDate(LocalDateTime.parse("2024-08-31T00:00:00"))
            .endDate(LocalDateTime.parse("2024-08-31T23:59:59"))
            .totalRequests(10000)
            .averageResponseSize(500)
            .percentile95ResponseSize(950)
            .topResources(Map.of(
                "/index.html", 5000L,
                "/about.html", 2000L,
                "/contact.html", 1000L
            ))
            .statusCodes(Map.of(
                200, 8000L,
                404, 1000L,
                500, 500L
            ))
            .requestMethods(Map.of(
                "GET", 9000L,
                "POST", 1000L
            ))
            .topClients(Map.of(
                "192.168.0.1", 500L,
                "192.168.0.2", 300L
            ))
            .build();
    }

    @Test
    public void testMarkdownFormatter() {
        // Arrange
        ReportFormatter formatter = new MarkdownFormatter();

        // Act
        String output = formatter.format(report);

        // Assert
        assertThat(output).isNotNull();
        assertThat(output).contains("#### Общая информация");
        assertThat(output).contains("| Метрика | Значение |");
        assertThat(output).contains("Файл(-ы)");
        assertThat(output).contains("access.log");
        assertThat(output).contains("Начальная дата");
        assertThat(output).contains("2024-08-31T00:00:00");
        assertThat(output).contains("Конечная дата");
        assertThat(output).contains("2024-08-31T23:59:59");
        assertThat(output).contains("Количество запросов");
        assertThat(output).contains("10000");
        assertThat(output).contains("Средний размер ответа");
        assertThat(output).contains("500.00 b"); // Изменено на точку
        assertThat(output).contains("95p размера ответа");
        assertThat(output).contains("950.00 b"); // Изменено на точку
        assertThat(output).contains("| /index.html | 5000 |");
        assertThat(output).contains("| 200 | 8000 |");
        assertThat(output).contains("| GET | 9000 |");
        assertThat(output).contains("| 192.168.0.1 | 500 |");
    }

    @Test
    public void testAsciidocFormatter() {
        // Arrange
        ReportFormatter formatter = new AsciidocFormatter();

        // Act
        String output = formatter.format(report);

        // Assert
        assertThat(output).isNotNull();
        assertThat(output).contains("==== Общая информация");
        assertThat(output).contains("| Метрика | Значение");
        assertThat(output).contains("Файл(-ы)");
        assertThat(output).contains("access.log");
        assertThat(output).contains("Начальная дата");
        assertThat(output).contains("2024-08-31T00:00:00");
        assertThat(output).contains("Конечная дата");
        assertThat(output).contains("2024-08-31T23:59:59");
        assertThat(output).contains("Количество запросов");
        assertThat(output).contains("10000");
        assertThat(output).contains("Средний размер ответа");
        assertThat(output).contains("500.00 b"); // Изменено на точку
        assertThat(output).contains("95p размера ответа");
        assertThat(output).contains("950.00 b"); // Изменено на точку
        assertThat(output).contains("| /index.html | 5000");
        assertThat(output).contains("| 200 | 8000");
        assertThat(output).contains("| GET | 9000");
        assertThat(output).contains("| 192.168.0.1 | 500");
    }
}
