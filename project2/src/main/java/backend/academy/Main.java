package backend.academy;

import backend.academy.ui.ConsoleUserInterface;
import backend.academy.ui.UserInterface;
import lombok.experimental.UtilityClass;

/**
 * Главный класс приложения.
 */
@UtilityClass
public class Main {
    public static void main(String[] args) {
        UserInterface ui = new ConsoleUserInterface();
        ui.run();
    }
}
