package backend.academy.game.service;

import backend.academy.game.exception.InvalidInputException;

/**
 * Отвечает за валидацию ввода пользователя.
 */
public class ValidationService {

    public void validateInput(String input) throws InvalidInputException {
        if (input.equalsIgnoreCase("hint") || input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("restart")) {
            return;
        }
        if (input.length() != 1 || !isRussianLetter(input.charAt(0))) {
            throw new InvalidInputException("Введите одну русскую букву или команду 'hint', 'exit', 'restart'.");
        }
    }

    private boolean isRussianLetter(char c) {
        char lowerC = Character.toLowerCase(c);
        return (lowerC >= 'а' && lowerC <= 'я') || lowerC == 'ё';
    }
}
