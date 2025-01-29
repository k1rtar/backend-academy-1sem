package backend.academy.game.repository;

import backend.academy.game.model.Word;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class WordRepositoryTest {

    private WordRepository wordRepository;
    private Path wordFilePath;

    @BeforeEach
    void setUp() throws URISyntaxException {
        wordRepository = new WordRepository();
        wordFilePath = Paths.get("src", "main", "resources", "words.txt");
    }

    @Test
    void givenValidFilePath_whenLoadWords_thenReturnPopulatedWordMap() {
        // Act
        Map<String, Map<String, List<Word>>> words = wordRepository.loadWords(wordFilePath.toString());

        // Assert
        assertThat(words).isNotEmpty();
        assertThat(words.containsKey("Животные")).isTrue();
        assertThat(words.get("Животные").containsKey("easy")).isTrue();
        assertThat(words.get("Животные").get("easy")).hasSizeGreaterThan(0);
    }

    @Test
    void givenInvalidFilePath_whenLoadWords_thenThrowException() {
        // Arrange
        String invalidFilePath = "non_existent_file.txt";

        // Act & Assert
        assertThatThrownBy(() -> wordRepository.loadWords(invalidFilePath))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Файл словаря не найден");
    }
}
