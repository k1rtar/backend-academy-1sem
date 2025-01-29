package backend.academy.config;

import backend.academy.utils.DateUtils;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class Config {


    @Parameter(names = {"--path"},
        description = "Путь к лог-файлам (может быть шаблоном или URL)",
        required = true, variableArity = true)
    private List<String> paths;

    @Parameter(names = {"--from"},
        description = "Начальная дата в формате ISO8601 (опционально)",
        converter = DateUtils.LocalDateTimeConverter.class)
    private LocalDateTime from;

    @Parameter(names = {"--to"},
        description = "Конечная дата в формате ISO8601 (опционально)",
        converter = DateUtils.LocalDateTimeConverter.class)
    private LocalDateTime to;

    @Parameter(names = {"--format"}, description = "Формат вывода отчёта (markdown|adoc)")
    private String format = "markdown";

    @Parameter(names = {"--filter-field"}, description = "Поле для фильтрации (agent, method, status и т.д.)")
    private String filterField;

    @Parameter(names = {"--filter-value"}, description = "Значение для фильтрации (поддерживает *)")
    private String filterValue;

    @Parameter(names = {"--encoding"}, description = "Кодировка лог-файлов")
    private String encoding;

    @Parameter(names = {"--help", "-h"}, help = true, description = "Показать эту справку")
    private boolean help;

    public static Config parseArgs(String[] args) {
        Config config = new Config();
        JCommander jc = JCommander.newBuilder()
            .addObject(config)
            .build();
        jc.parse(args);

        if (config.help) {
            jc.usage();
            System.exit(0);
        }

        return config;
    }
}
