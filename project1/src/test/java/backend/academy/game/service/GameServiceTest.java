package backend.academy.game.service;

import backend.academy.game.model.GameSession;
import backend.academy.game.model.Word;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class GameServiceTest {

    private final GameService gameService = new GameService();

    @Test
    void givenWordAndMaxAttempts_whenCreateGameSession_thenSessionCreated() {
        // Arrange
        Word word = new Word("тест", "подсказка");
        int maxAttempts = 7;

        // Act
        GameSession session = gameService.createGameSession(word, maxAttempts);

        // Assert
        assertThat(session).isNotNull();
        assertThat(session.getWordToGuess()).isEqualTo("тест");
        assertThat(session.getHint()).isEqualTo("подсказка");
        assertThat(session.getMaxAttempts()).isEqualTo(maxAttempts);
    }

    @Test
    void givenDifficulty_whenGetDefaultAttempts_thenReturnsCorrectAttempts() {
        assertThat(gameService.getDefaultAttempts("easy")).isEqualTo(10);
        assertThat(gameService.getDefaultAttempts("medium")).isEqualTo(7);
        assertThat(gameService.getDefaultAttempts("hard")).isEqualTo(5);
        assertThat(gameService.getDefaultAttempts("unknown")).isEqualTo(7);
    }
}
