package backend.academy.game.repository;

import backend.academy.game.model.Word;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.log4j.Log4j2;

/**
 * Класс для загрузки слов из файла на основе пути к файлу.
 */
@Log4j2
public class WordRepository {

    private static final int EXPECTED_PARTS_LENGTH = 4;
    private static final int CATEGORY_INDEX = 0;
    private static final int DIFFICULTY_INDEX = 1;
    private static final int WORD_INDEX = 2;
    private static final int HINT_INDEX = 3;
    private static final String LOAD_ERROR_MESSAGE = "Ошибка при загрузке слов";

    public Map<String, Map<String, List<Word>>> loadWords(String filePath) {
        Map<String, Map<String, List<Word>>> loadedWords = new HashMap<>();

        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            log.error("Файл {} не найден. Не удалось загрузить слова.", filePath);
            throw new IllegalArgumentException("Файл словаря не найден: " + filePath);
        }

        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                String[] parts = line.split("\\|", EXPECTED_PARTS_LENGTH);
                if (parts.length < EXPECTED_PARTS_LENGTH) {
                    log.warn("Некорректная строка в файле конфигурации: {}", line);
                    continue;
                }

                String category = parts[CATEGORY_INDEX].trim();
                String difficulty = parts[DIFFICULTY_INDEX].trim();
                String wordText = parts[WORD_INDEX].trim();
                String hint = parts[HINT_INDEX].trim();

                Word word = new Word(wordText, hint);
                loadedWords
                    .computeIfAbsent(category, k -> new HashMap<>())
                    .computeIfAbsent(difficulty, k -> new ArrayList<>())
                    .add(word);
            }
        } catch (IOException e) {
            log.error(LOAD_ERROR_MESSAGE, e);
            throw new RuntimeException(LOAD_ERROR_MESSAGE, e);
        }
        return loadedWords;
    }
}
