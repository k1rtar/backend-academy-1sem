package backend.academy.game.service;

import backend.academy.game.model.Word;
import backend.academy.game.repository.WordRepository;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

/**
 * Отвечает за предоставление случайного слова из репозитория.
 */
@Log4j2
@Getter
public class WordService {

    private static final String EMPTY_DICTIONARY_ERROR =
        "Словарь пуст после загрузки. Проверьте конфигурационный файл.";
    private static final String NO_WORDS_FOUND_ERROR =
        "Нет слов для выбранной категории и сложности.";

    private final Map<String, Map<String, List<Word>>> wordsByCategory;
    private final Random random = new Random();

    public WordService(WordRepository wordRepository, String filePath) {
        this.wordsByCategory = wordRepository.loadWords(filePath);
        validateDictionary();
    }

    /**
     * Получение случайного слова из словаря на основе категории и сложности.
     */
    public Word getRandomWord(String category, String difficulty) {
        List<Word> words = wordsByCategory.getOrDefault(category, Collections.emptyMap())
            .getOrDefault(difficulty, Collections.emptyList());

        if (words.isEmpty()) {
            log.warn("Нет слов для категории '{}' и сложности '{}'", category, difficulty);
            throw new IllegalArgumentException(NO_WORDS_FOUND_ERROR);
        }
        return words.get(random.nextInt(words.size()));
    }

    /**
     * Получить доступные категории.
     */
    public Set<String> getAvailableCategories() {
        return wordsByCategory.keySet();
    }

    /**
     * Получить доступные уровни сложности для конкретной категории.
     */
    public Set<String> getAvailableDifficulties(String category) {
        return wordsByCategory.getOrDefault(category, Collections.emptyMap()).keySet();
    }

    /**
     * Валидация словаря на наличие пустых категорий.
     */
    private void validateDictionary() {
        if (wordsByCategory.isEmpty()) {
            log.error(EMPTY_DICTIONARY_ERROR);
            throw new IllegalStateException(EMPTY_DICTIONARY_ERROR);
        }

        for (Map.Entry<String, Map<String, List<Word>>> categoryEntry : wordsByCategory.entrySet()) {
            String category = categoryEntry.getKey();
            Map<String, List<Word>> difficulties = categoryEntry.getValue();

            for (Map.Entry<String, List<Word>> difficultyEntry : difficulties.entrySet()) {
                List<Word> words = difficultyEntry.getValue();
                if (words.isEmpty()) {
                    log.warn("Категория '{}' и сложность '{}' не содержат слов",
                        category, difficultyEntry.getKey());
                    throw new IllegalStateException(
                        String.format("Категория '%s' и сложность '%s' не содержат слов",
                            category, difficultyEntry.getKey())
                    );
                }
            }
        }
    }
}
