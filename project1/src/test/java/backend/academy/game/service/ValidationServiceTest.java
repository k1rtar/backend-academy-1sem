package backend.academy.game.service;

import backend.academy.game.exception.InvalidInputException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ValidationServiceTest {

    private final ValidationService validationService = new ValidationService();

    @Test
    void givenValidRussianLetter_whenValidateInput_thenNoException() throws InvalidInputException {
        // Arrange
        String input = "а";

        // Act & Assert
        validationService.validateInput(input);
    }

    @Test
    void givenUpperCaseRussianLetter_whenValidateInput_thenNoException() throws InvalidInputException {
        // Arrange
        String input = "Б";

        // Act & Assert
        validationService.validateInput(input);
    }

    @Test
    void givenCommandHint_whenValidateInput_thenNoException() throws InvalidInputException {
        // Arrange
        String input = "hint";

        // Act & Assert
        validationService.validateInput(input);
    }

    @Test
    void givenCommandExit_whenValidateInput_thenNoException() throws InvalidInputException {
        // Arrange
        String input = "exit";

        // Act & Assert
        validationService.validateInput(input);
    }

    @Test
    void givenCommandRestart_whenValidateInput_thenNoException() throws InvalidInputException {
        // Arrange
        String input = "restart";

        // Act & Assert
        validationService.validateInput(input);
    }

    @Test
    void givenEnglishLetter_whenValidateInput_thenThrowsException() {
        // Arrange
        String input = "a";

        // Act & Assert
        assertThatThrownBy(() -> validationService.validateInput(input))
            .isInstanceOf(InvalidInputException.class)
            .hasMessageContaining("Введите одну русскую букву");
    }

    @Test
    void givenMultipleLetters_whenValidateInput_thenThrowsException() {
        // Arrange
        String input = "аб";

        // Act & Assert
        assertThatThrownBy(() -> validationService.validateInput(input))
            .isInstanceOf(InvalidInputException.class)
            .hasMessageContaining("Введите одну русскую букву");
    }

    @Test
    void givenNumber_whenValidateInput_thenThrowsException() {
        // Arrange
        String input = "1";

        // Act & Assert
        assertThatThrownBy(() -> validationService.validateInput(input))
            .isInstanceOf(InvalidInputException.class)
            .hasMessageContaining("Введите одну русскую букву");
    }

    @Test
    void givenSpecialCharacter_whenValidateInput_thenThrowsException() {
        // Arrange
        String input = "@";

        // Act & Assert
        assertThatThrownBy(() -> validationService.validateInput(input))
            .isInstanceOf(InvalidInputException.class)
            .hasMessageContaining("Введите одну русскую букву");
    }
}
