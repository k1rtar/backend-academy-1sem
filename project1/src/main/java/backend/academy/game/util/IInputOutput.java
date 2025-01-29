package backend.academy.game.util;

/**
 * Интерфейс для ввода/вывода, позволяющий облегчить тестирование.
 */
public interface IInputOutput extends AutoCloseable {

    void print(String message);

    String readLine();
}
