package backend.academy.game.exception;

/**
 * Исключение для неверного ввода.
 */
public class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}
