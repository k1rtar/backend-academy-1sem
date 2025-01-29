package backend.academy.game.util;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Реализация ввода/вывода через переданные потоки.
 */
public class InputOutput implements IInputOutput {

    private final Scanner scanner;
    private final PrintStream out;

    public InputOutput(InputStream in, PrintStream out) {
        this.scanner = new Scanner(in);
        this.out = out;
    }

    @Override
    public void print(String message) {
        out.println(message);
    }

    @Override
    public String readLine() {
        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        } else {
            return null;
        }
    }

    @Override
    public void close() {
        scanner.close();
    }

}
