package backend.academy.formatter;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ReportFormatterFactory {
    public static ReportFormatter create(String format) {
        switch (format.toLowerCase()) {
            case "markdown":
                return new MarkdownFormatter();
            case "adoc":
                return new AsciidocFormatter();
            default:
                throw new IllegalArgumentException("Неизвестный формат отчета: " + format);
        }
    }
}
