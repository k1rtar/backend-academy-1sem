package backend.academy.formatter;

import backend.academy.model.LogReport;

public interface ReportFormatter {
    String format(LogReport report);
}
