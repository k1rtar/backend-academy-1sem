package backend.academy.game.state;

import backend.academy.game.controller.GameController;
import backend.academy.game.exception.InvalidInputException;
import backend.academy.game.model.GameSession;
import backend.academy.game.service.ValidationService;
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
class PlayStateTest {

    @Mock
    private IInputOutput ioMock;
    @Mock
    private ValidationService validationServiceMock;
    @Mock
    private MessageProvider messageProviderMock;
    @Mock
    private WordService wordServiceMock;

    @InjectMocks
    private GameController gameController;

    private GameSession gameSession;
    private PlayState playState;

    @BeforeEach
    void setUp() {
        gameController.setValidationService(validationServiceMock);
        gameController.setMessageProvider(messageProviderMock);

        gameSession = new GameSession("кот", "Домашнее животное, которое мурлычет", 5);
        gameController.setGameSession(gameSession);

        playState = new PlayState();
    }

    @Test
    void givenPlayerInputsExitCommand_whenExecute_thenGameEnds() throws InvalidInputException {
        // Arrange
        when(ioMock.readLine()).thenReturn("exit");
        doNothing().when(validationServiceMock).validateInput("exit");

        // Act
        playState.execute(gameController);

        // Assert
        verify(ioMock).print(contains("Выход из игры..."));
        assertThat(gameController.getCurrentState()).isNull();
    }

    @Test
    void givenPlayerGuessesCorrectLetter_whenExecute_thenLetterRevealed() throws InvalidInputException {
        // Arrange
        when(ioMock.readLine()).thenReturn("к", "о", "т");
        doNothing().when(validationServiceMock).validateInput(anyString());

        // Act
        playState.execute(gameController);

        // Assert
        assertThat(gameController.getCurrentState()).isInstanceOf(WinState.class);
        assertThat(gameSession.isWordGuessed()).isTrue();
    }

    @Test
    void givenPlayerGuessesIncorrectLetter_whenExecute_thenAttemptsIncrease() throws InvalidInputException {
        // Arrange
        when(ioMock.readLine()).thenReturn("а", "б", "в", "г", "д");
        doNothing().when(validationServiceMock).validateInput(anyString());

        // Act
        playState.execute(gameController);

        // Assert
        assertThat(gameController.getCurrentState()).isInstanceOf(LoseState.class);
        assertThat(gameSession.getWrongAttempts()).isEqualTo(5);
    }

    @Test
    void givenPlayerEntersMultipleLetters_whenExecute_thenPromptReentry() throws InvalidInputException {
        // Arrange
        when(ioMock.readLine()).thenReturn("аб", "к", "о", "т");
        doAnswer(invocation -> {
            String input = invocation.getArgument(0);
            if (input.length() != 1) {
                throw new InvalidInputException("Введите одну русскую букву или команду 'hint', 'exit', 'restart'.");
            }
            return null;
        }).when(validationServiceMock).validateInput(anyString());

        // Act
        playState.execute(gameController);

        // Assert
        verify(ioMock, times(1)).print(contains("Введите одну русскую букву"));
        assertThat(gameController.getCurrentState()).isInstanceOf(WinState.class);
    }
}
