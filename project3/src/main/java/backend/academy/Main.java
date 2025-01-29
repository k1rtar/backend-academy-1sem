package backend.academy;

import backend.academy.config.Config;
import backend.academy.filter.LogFilter;
import backend.academy.formatter.ReportFormatter;
import backend.academy.formatter.ReportFormatterFactory;
import backend.academy.model.LogRecord;
import backend.academy.model.LogReport;
import backend.academy.parser.LogParser;
import backend.academy.reader.LogReader;
import backend.academy.reader.LogReaderFactory;
import backend.academy.statistics.StatisticsCollector;
import backend.academy.utils.Messages;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Objects;
import java.util.stream.Stream;
import lombok.experimental.UtilityClass;

@UtilityClass
@SuppressWarnings("checkstyle:RegexpSinglelineJava")
public class Main {

    public static void main(String[] args) {
        try {
            Config config = Config.parseArgs(args);

            Charset charset = config.getEncoding() != null
                ? Charset.forName(config.getEncoding())
                : StandardCharsets.UTF_8;

            LogReader logReader = LogReaderFactory.create(config.getPaths(), charset);
            LogParser logParser = new LogParser();
            LogFilter logFilter = new LogFilter(config);
            StatisticsCollector statsCollector = new StatisticsCollector();
            ReportFormatter formatter = ReportFormatterFactory.create(config.getFormat());

            try (Stream<String> lines = logReader.readLines()) {
                Stream<LogRecord> records = lines
                    .map(logParser::parse)
                    .filter(Objects::nonNull)
                    .filter(logFilter::filter);

                LogReport report = statsCollector.collect(records, config);

                String output = formatter.format(report);
                System.out.println(output);
            } catch (UncheckedIOException e) {
                handleIOException(e.getCause());
            } catch (IllegalArgumentException e) {
                System.err.println(String.format(Messages.ERROR_ARGUMENTS, e.getMessage()));
            } catch (Exception e) {
                System.err.println(String.format(Messages.ERROR_UNEXPECTED, e.getMessage()));
            }
        } catch (UnsupportedCharsetException e) {
            System.err.println(Messages.ERROR_UNSUPPORTED_CHARSET);
        } catch (IllegalArgumentException e) {
            System.err.println(String.format(Messages.ERROR_ARGUMENTS, e.getMessage()));
        } catch (Exception e) {
            System.err.println(String.format(Messages.ERROR_UNEXPECTED, e.getMessage()));
        }
    }

    private static void handleIOException(Throwable cause) {
        if (cause instanceof IOException) {
            String message = cause.getMessage();
            if (message.contains("MalformedInputException")) {
                System.err.println(Messages.ERROR_MALFORMED_INPUT);
            } else if (message.contains("NoSuchFileException")) {
                System.err.println(Messages.ERROR_FILE_NOT_FOUND);
            } else {
                System.err.println(String.format(Messages.ERROR_IO_EXCEPTION, message));
            }
        } else {
            System.err.println(String.format(Messages.ERROR_UNEXPECTED, cause.getMessage()));
        }
    }
}
