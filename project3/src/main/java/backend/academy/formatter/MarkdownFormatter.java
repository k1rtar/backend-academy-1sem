package backend.academy.formatter;

public class MarkdownFormatter extends AbstractReportFormatter {

    private static final String DOUBLE_NEWLINE = "\n\n";
    private static final String TABLE_START = "";
    private static final String TABLE_END = "\n";
    private static final String ROW_FORMAT = "| %s | %s |\n";
    private static final String HEADER_SEPARATOR = "|:---|---:|\n";
    private static final String SECTION_TITLE_FORMAT = "#### %s\n\n";

    @Override
    protected String formatSectionTitle(String title) {
        return String.format(SECTION_TITLE_FORMAT, title);
    }

    @Override
    protected String formatTableStart() {
        return "";
    }

    @Override
    protected String formatTableEnd() {
        return "\n";
    }

    @Override
    protected String formatTableRow(String... columns) {
        return String.format(ROW_FORMAT, columns);
    }

    @Override
    protected String formatHeaderRow(String... columns) {
        return String.format(ROW_FORMAT, columns) + HEADER_SEPARATOR;
    }
}
