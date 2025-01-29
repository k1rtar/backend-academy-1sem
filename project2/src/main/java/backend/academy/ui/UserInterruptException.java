package backend.academy.ui;

/**
 * Исключение, указывающее на прерывание ввода пользователем.
 */
public class UserInterruptException extends RuntimeException {
    public UserInterruptException(String message) {
        super(message);
    }
}
