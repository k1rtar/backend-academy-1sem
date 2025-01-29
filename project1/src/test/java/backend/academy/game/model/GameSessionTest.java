package backend.academy.game.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class GameSessionTest {

    @Test
    void givenAllLettersGuessed_whenIsWordGuessed_thenReturnsTrue() {
        // Arrange
        GameSession session = new GameSession("тест", "подсказка", 7);

        // Act
        session.addGuessedLetter('т');
        session.addGuessedLetter('е');
        session.addGuessedLetter('с');

        // Assert
        assertThat(session.isWordGuessed()).isTrue();
    }

    @Test
    void givenNotAllLettersGuessed_whenIsWordGuessed_thenReturnsFalse() {
        // Arrange
        GameSession session = new GameSession("тест", "подсказка", 7);

        // Act
        session.addGuessedLetter('т');
        session.addGuessedLetter('е');

        // Assert
        assertThat(session.isWordGuessed()).isFalse();
    }

    @Test
    void whenIncrementWrongAttempts_thenWrongAttemptsIncreased() {
        // Arrange
        GameSession session = new GameSession("тест", "подсказка", 7);

        // Act
        session.incrementWrongAttempts();
        session.incrementWrongAttempts();

        // Assert
        assertThat(session.getWrongAttempts()).isEqualTo(2);
    }

    @Test
    void whenAddGuessedAndUsedLetters_thenLettersAddedToSets() {
        // Arrange
        GameSession session = new GameSession("тест", "подсказка", 7);

        // Act
        session.addGuessedLetter('т');
        session.addUsedLetter('р');

        // Assert
        assertThat(session.getGuessedLetters()).contains('т');
        assertThat(session.getUsedLetters()).contains('т', 'р');
    }
}
