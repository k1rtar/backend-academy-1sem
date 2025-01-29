package backend.academy.game.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class MessageProviderTest {

    @Test
    void givenKey_whenGetMessage_thenReturnsCorrectMessage() {
        // Arrange
        MessageProvider messageProvider = new MessageProvider();

        // Act
        String welcomeMessage = messageProvider.getMessage("welcome");

        // Assert
        assertThat(welcomeMessage).isEqualTo("Добро пожаловать в игру Виселица!");
    }

    @Test
    void givenKeyWithParameters_whenGetMessage_thenReturnsFormattedMessage() {
        // Arrange
        MessageProvider messageProvider = new MessageProvider();

        // Act
        String winMessage = messageProvider.getMessage("win_message", "кот");

        // Assert
        assertThat(winMessage).isEqualTo("Поздравляем! Вы угадали слово: кот");
    }
}
