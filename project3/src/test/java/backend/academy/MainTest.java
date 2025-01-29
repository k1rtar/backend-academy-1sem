
package backend.academy;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import backend.academy.config.Config;
import backend.academy.filter.LogFilter;
import backend.academy.formatter.ReportFormatter;
import backend.academy.formatter.ReportFormatterFactory;
import backend.academy.model.LogReport;
import backend.academy.parser.LogParser;
import backend.academy.reader.LogReader;
import backend.academy.reader.LogReaderFactory;
import backend.academy.statistics.StatisticsCollector;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

public class MainTest {

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    public void setUpStreams() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testMainFlow() throws Exception {
        // Arrange
        String[] args = {"--path", "logs/access.log", "--format", "markdown"};

        Config config = mock(Config.class);
        when(config.getPaths()).thenReturn(List.of("logs/access.log"));
        when(config.getFormat()).thenReturn("markdown");
        when(config.getEncoding()).thenReturn(null);
        when(config.isHelp()).thenReturn(false);

        LogReader logReader = mock(LogReader.class);
        when(logReader.readLines()).thenReturn(Stream.of("log line"));

        LogReport logReport = mock(LogReport.class);
        when(logReport.toString()).thenReturn("Formatted Report");

        ReportFormatter formatter = mock(ReportFormatter.class);
        when(formatter.format(any(LogReport.class))).thenReturn("Formatted Report");

        try (MockedStatic<Config> mockedConfig = mockStatic(Config.class);
             MockedStatic<LogReaderFactory> mockedLogReaderFactory = mockStatic(LogReaderFactory.class);
             MockedStatic<ReportFormatterFactory> mockedFormatterFactory = mockStatic(ReportFormatterFactory.class)) {


            mockedConfig.when(() -> Config.parseArgs(args)).thenReturn(config);


            mockedLogReaderFactory.when(() -> LogReaderFactory.create(any(), any(Charset.class))).thenReturn(logReader);


            mockedFormatterFactory.when(() -> ReportFormatterFactory.create("markdown")).thenReturn(formatter);

            // Act
            Main.main(args);

            // Assert
            verify(logReader).readLines();
            verify(formatter).format(any(LogReport.class));

            assertThat(outContent.toString()).contains("Formatted Report");
        }
    }

}
