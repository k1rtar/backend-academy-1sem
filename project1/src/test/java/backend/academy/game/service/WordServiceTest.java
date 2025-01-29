package backend.academy.game.service;

import backend.academy.game.repository.WordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WordServiceTest {

    private WordService wordService;

    @BeforeEach
    void setUp() throws URISyntaxException {
        Path wordFilePath = Paths.get("src",  "main", "resources", "words.txt");
        WordRepository wordRepository = new WordRepository();
        wordService = new WordService(wordRepository, wordFilePath.toString());
    }

    @Test
    void givenCategoriesAndDifficulties_whenGetRandomWord_thenWordReturned() {
        // Arrange
        var categories = wordService.getAvailableCategories();
        assertThat(categories).isNotEmpty();

        // Act & Assert
        for (String category : categories) {
            var difficulties = wordService.getAvailableDifficulties(category);
            assertThat(difficulties).isNotEmpty();

            for (String difficulty : difficulties) {
                var word = wordService.getRandomWord(category, difficulty);
                assertThat(word).isNotNull();
                assertThat(word.getWord()).isNotEmpty();
                assertThat(word.getHint()).isNotEmpty();
            }
        }
    }

    @Test
    void givenInvalidCategory_whenGetRandomWord_thenThrowsException() {
        // Arrange
        String invalidCategory = "НесуществующаяКатегория";

        // Act & Assert
        assertThatThrownBy(() -> wordService.getRandomWord(invalidCategory, "easy"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Нет слов для выбранной категории и сложности.");
    }
}
