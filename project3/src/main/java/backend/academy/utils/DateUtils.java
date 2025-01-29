package backend.academy.utils;

import com.beust.jcommander.IStringConverter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DateUtils {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public static LocalDateTime parse(String dateStr) {
        return LocalDateTime.parse(dateStr, DATE_FORMATTER);
    }

    public static String format(LocalDateTime dateTime) {
        return dateTime.format(DATE_FORMATTER);
    }

    public static class LocalDateTimeConverter implements IStringConverter<LocalDateTime> {
        @Override
        public LocalDateTime convert(String value) {
            return DateUtils.parse(value);
        }
    }
}
