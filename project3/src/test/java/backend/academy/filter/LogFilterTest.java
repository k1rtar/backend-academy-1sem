package backend.academy.filter;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import backend.academy.config.Config;
import backend.academy.model.LogRecord;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class LogFilterTest {

    @Test
    public void testFilter_WithTimeAndFieldMatching() {
        // Arrange
        Config config = mock(Config.class);
        when(config.getFrom()).thenReturn(LocalDateTime.parse("2023-01-01T00:00:00"));
        when(config.getTo()).thenReturn(LocalDateTime.parse("2023-12-31T23:59:59"));
        when(config.getFilterField()).thenReturn("status");
        when(config.getFilterValue()).thenReturn("200");

        LogFilter logFilter = new LogFilter(config);

        LogRecord logRecord = LogRecord.builder()
            .timeLocal(LocalDateTime.parse("2023-06-15T12:00:00"))
            .status(200)
            .build();

        // Act
        boolean result = logFilter.filter(logRecord);

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    public void testFilter_WithTimeOutsideRange() {
        // Arrange
        Config config = mock(Config.class);
        when(config.getFrom()).thenReturn(LocalDateTime.parse("2023-01-01T00:00:00"));
        when(config.getTo()).thenReturn(LocalDateTime.parse("2023-12-31T23:59:59"));

        LogFilter logFilter = new LogFilter(config);

        LogRecord logRecord = LogRecord.builder()
            .timeLocal(LocalDateTime.parse("2024-01-01T12:00:00"))
            .build();

        // Act
        boolean result = logFilter.filter(logRecord);

        // Assert
        assertThat(result).isFalse();
    }

    @Test
    public void testFilter_WithFieldNotMatching() {
        // Arrange
        Config config = mock(Config.class);
        when(config.getFilterField()).thenReturn("method");
        when(config.getFilterValue()).thenReturn("POST");

        LogFilter logFilter = new LogFilter(config);

        LogRecord logRecord = LogRecord.builder()
            .method("GET")
            .build();

        // Act
        boolean result = logFilter.filter(logRecord);

        // Assert
        assertThat(result).isFalse();
    }

    @Test
    public void testFilter_WithNoFilters() {
        // Arrange
        Config config = mock(Config.class);
        when(config.getFilterField()).thenReturn(null);
        when(config.getFilterValue()).thenReturn(null);
        when(config.getFrom()).thenReturn(null);
        when(config.getTo()).thenReturn(null);

        LogFilter logFilter = new LogFilter(config);

        LogRecord logRecord = LogRecord.builder()
            .method("GET")
            .build();

        // Act
        boolean result = logFilter.filter(logRecord);

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    public void testFilter_WithUnknownField_ShouldThrowException() {
        // Arrange
        Config config = mock(Config.class);
        when(config.getFilterField()).thenReturn("unknown");
        when(config.getFilterValue()).thenReturn("value");

        LogFilter logFilter = new LogFilter(config);

        LogRecord logRecord = LogRecord.builder().build();

        // Act & Assert
        assertThatThrownBy(() -> logFilter.filter(logRecord))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Неизвестное поле для фильтрации");
    }
}

