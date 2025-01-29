package backend.academy.config;

import static org.assertj.core.api.Assertions.*;

import com.beust.jcommander.ParameterException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class ConfigTest {

    @Test
    public void testParseArgs_WithAllParameters() {
        // Arrange
        String[] args = {
            "--path", "logs/*.log",
            "--from", "2024-08-31T00:00:00",
            "--to", "2024-08-31T23:59:59",
            "--format", "adoc",
            "--filter-field", "method",
            "--filter-value", "GET",
            "--encoding", "UTF-8"
        };

        // Act
        Config config = Config.parseArgs(args);

        // Assert
        assertThat(config.getPaths()).containsExactly("logs/*.log");
        assertThat(config.getFrom()).isEqualTo(LocalDateTime.parse("2024-08-31T00:00:00"));
        assertThat(config.getTo()).isEqualTo(LocalDateTime.parse("2024-08-31T23:59:59"));
        assertThat(config.getFormat()).isEqualTo("adoc");
        assertThat(config.getFilterField()).isEqualTo("method");
        assertThat(config.getFilterValue()).isEqualTo("GET");
        assertThat(config.getEncoding()).isEqualTo("UTF-8");
        assertThat(config.isHelp()).isFalse();
    }

    @Test
    public void testParseArgs_WithDefaultValues() {
        // Arrange
        String[] args = {"--path", "logs/*.log"};

        // Act
        Config config = Config.parseArgs(args);

        // Assert
        assertThat(config.getPaths()).containsExactly("logs/*.log");
        assertThat(config.getFrom()).isNull();
        assertThat(config.getTo()).isNull();
        assertThat(config.getFormat()).isEqualTo("markdown");
        assertThat(config.getFilterField()).isNull();
        assertThat(config.getFilterValue()).isNull();
        assertThat(config.getEncoding()).isNull();
        assertThat(config.isHelp()).isFalse();
    }

    @Test
    public void testParseArgs_MissingRequiredPath_ShouldThrowException() {
        // Arrange
        String[] args = {"--format", "markdown"};

        // Act
        Throwable thrown = catchThrowable(() -> Config.parseArgs(args));

        // Assert
        assertThat(thrown)
            .isInstanceOf(ParameterException.class)
            .hasMessageContaining("[--path]");
    }

}


