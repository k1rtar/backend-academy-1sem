package backend.academy.flame.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Загрузчик конфигурации из JSON.
 */
public final class ConfigLoader {
    private ConfigLoader() {}

    /**
     * Загружает конфигурацию из указанного пути.
     *
     * @param path путь к JSON-файлу с конфигурацией
     * @return объект Config, содержащий загруженные параметры
     * @throws IOException если возникает ошибка при чтении файла
     */
    public static Config load(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try (var in = Files.newInputStream(Path.of(path))) {
            return mapper.readValue(in, Config.class);
        }
    }
}
