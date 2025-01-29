package backend.academy.game.state;

import backend.academy.game.controller.GameController;
import backend.academy.game.model.GameSession;
import backend.academy.game.service.WordService;
import backend.academy.game.util.IInputOutput;
import backend.academy.game.util.MessageProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WinStateTest {

    @Mock
    private IInputOutput ioMock;
    @Mock
    private MessageProvider messageProviderMock;
    @Mock
    private WordService wordServiceMock;

    @InjectMocks
    private GameController gameController;

    private WinState winState;

    @BeforeEach
    void setUp() {
        gameController.setMessageProvider(messageProviderMock);
        gameController.setGameSession(new GameSession("кот", "Домашнее животное, которое мурлычет", 5));

        winState = new WinState();
    }

    @Test
    void givenPlayerChoosesToPlayAgain_whenExecute_thenGameResets() {
        // Arrange
        when(ioMock.readLine()).thenReturn("y");

        // Act
        winState.execute(gameController);

        // Assert
        verify(ioMock).print(contains("Хотите сыграть ещё раз? (y/n):"));
        assertThat(gameController.getCurrentState()).isInstanceOf(StartState.class);
    }

    @Test
    void givenPlayerChoosesNotToPlayAgain_whenExecute_thenGameEnds() {
        // Arrange
        when(ioMock.readLine()).thenReturn("n");

        // Act
        winState.execute(gameController);

        // Assert
        verify(ioMock).print(contains("Спасибо за игру!"));
        assertThat(gameController.getCurrentState()).isNull();
    }
}
