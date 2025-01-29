package backend.academy.filter;

import backend.academy.config.Config;
import backend.academy.model.LogRecord;
import java.time.LocalDateTime;

public class LogFilter {
    private final Config config;

    public LogFilter(Config config) {
        this.config = config;
    }

    public boolean filter(LogRecord logRecord) {
        return filterByTime(logRecord) && filterByField(logRecord);
    }

    private boolean filterByTime(LogRecord logRecord) {
        LocalDateTime from = config.getFrom();
        LocalDateTime to = config.getTo();

        if (from != null && logRecord.getTimeLocal().isBefore(from)) {
            return false;
        }
        if (to != null && logRecord.getTimeLocal().isAfter(to)) {
            return false;
        }
        return true;
    }

    private boolean filterByField(LogRecord logRecord) {
        String field = config.getFilterField();
        String value = config.getFilterValue();

        if (field == null || value == null) {
            return true;
        }

        String recordValue = getFieldValue(logRecord, field);
        return recordValue != null && recordValue.matches(value.replace("*", ".*"));
    }

    private String getFieldValue(LogRecord logRecord, String field) {
        switch (field.toLowerCase()) {
            case "agent":
                return logRecord.getHttpUserAgent();
            case "method":
                return logRecord.getMethod();
            case "resource":
                return logRecord.getResource();
            case "status":
                return String.valueOf(logRecord.getStatus());
            default:
                throw new IllegalArgumentException("Неизвестное поле для фильтрации: " + field);
        }
    }
}
