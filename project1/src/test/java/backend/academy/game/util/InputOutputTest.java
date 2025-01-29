package backend.academy.game.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

class InputOutputTest {

    private InputOutput io;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        String input = "Ввод пользователя";
        ByteArrayInputStream inContent = new ByteArrayInputStream(input.getBytes());

        outContent = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outContent);

        io = new InputOutput(inContent, printStream);
    }

    @AfterEach
    void tearDown() throws Exception {
        io.close();
    }

    @Test
    void givenMessage_whenPrint_thenMessagePrinted() {
        // Act
        io.print("Тестовое сообщение");

        // Assert
        assertThat(outContent.toString().trim()).isEqualTo("Тестовое сообщение");
    }

    @Test
    void givenInput_whenReadLine_thenReturnsInput() {
        // Act
        String result = io.readLine();

        // Assert
        assertThat(result).isEqualTo("Ввод пользователя");
    }
}
