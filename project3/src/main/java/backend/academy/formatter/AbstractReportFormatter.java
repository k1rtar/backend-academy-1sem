package backend.academy.formatter;

import backend.academy.model.LogReport;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

public abstract class AbstractReportFormatter implements ReportFormatter {

    protected static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private static final String COUNT = "Количество";
    private static final String REQUEST_COUNT = "Количество запросов";
    private static final String BYTE_FORMAT = "%.2f b";


    protected abstract String formatSectionTitle(String title);

    protected abstract String formatTableStart();

    protected abstract String formatTableEnd();

    protected abstract String formatTableRow(String... columns);

    protected abstract String formatHeaderRow(String... columns);

    protected String formatDate(LocalDateTime date) {
        return date != null ? date.format(DATE_FORMATTER) : "-";
    }

    @Override
    public String format(LogReport report) {
        StringBuilder sb = new StringBuilder();

        // Общая информация
        sb.append(formatSectionTitle("Общая информация"));
        sb.append(formatGeneralInfo(report));

        // Запрашиваемые ресурсы
        sb.append(formatSectionTitle("Запрашиваемые ресурсы"));
        sb.append(formatTable("Ресурс", COUNT, report.getTopResources()));

        // Коды ответа
        sb.append(formatSectionTitle("Коды ответа"));
        sb.append(formatTable("Код", COUNT, report.getStatusCodes()));

        // Методы запросов
        sb.append(formatSectionTitle("Методы запросов"));
        sb.append(formatTable("Метод", COUNT, report.getRequestMethods()));

        // Топ клиентов
        sb.append(formatSectionTitle("Топ клиентов"));
        sb.append(formatTable("IP-адрес", REQUEST_COUNT, report.getTopClients()));

        return sb.toString();
    }

    private String formatGeneralInfo(LogReport report) {
        StringBuilder sb = new StringBuilder();
        sb.append(formatTableStart());
        sb.append(formatHeaderRow("Метрика", "Значение"));
        sb.append(formatTableRow("Файл(-ы)", String.join(", ", report.getFiles())));
        sb.append(formatTableRow("Начальная дата", formatDate(report.getStartDate())));
        sb.append(formatTableRow("Конечная дата", formatDate(report.getEndDate())));
        sb.append(formatTableRow(REQUEST_COUNT, String.valueOf(report.getTotalRequests())));
        sb.append(formatTableRow("Средний размер ответа", String.format(Locale.US, BYTE_FORMAT,
            report.getAverageResponseSize())));
        sb.append(formatTableRow("95p размера ответа",
            String.format(Locale.US, BYTE_FORMAT, report.getPercentile95ResponseSize())));
        sb.append(formatTableEnd());
        return sb.toString();
    }

    private <K, V> String formatTable(String header1, String header2, Map<K, V> data) {
        StringBuilder sb = new StringBuilder();
        sb.append(formatTableStart());
        sb.append(formatHeaderRow(header1, header2));
        data.forEach((key, value) -> sb.append(formatTableRow(key.toString(), value.toString())));
        sb.append(formatTableEnd());
        return sb.toString();
    }
}
