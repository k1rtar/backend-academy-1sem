package backend.academy.game.controller;

import backend.academy.game.model.GameConfig;
import backend.academy.game.model.GameSession;
import backend.academy.game.model.Word;
import backend.academy.game.service.GameService;
import backend.academy.game.service.ValidationService;
import backend.academy.game.service.WordService;
import backend.academy.game.state.GameState;
import backend.academy.game.state.StartState;
import backend.academy.game.util.IInputOutput;
import backend.academy.game.util.MessageProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameControllerTest {

    @Mock
    private IInputOutput ioMock;
    @Mock
    private WordService wordServiceMock;
    @Mock
    private ValidationService validationServiceMock;
    @Mock
    private GameService gameServiceMock;
    @Mock
    private MessageProvider messageProviderMock;
    @Mock
    private GameState initialStateMock;

    @InjectMocks
    private GameController gameController;

    @BeforeEach
    void setUp() {
        gameController.setValidationService(validationServiceMock);
        gameController.setGameService(gameServiceMock);
        gameController.setMessageProvider(messageProviderMock);
        gameController.setCurrentState(initialStateMock);
    }

    @Test
    void givenInitialState_whenStartGame_thenStateExecuted() {
        // Arrange
        doAnswer(invocation -> {
            gameController.setCurrentState(null);
            return null;
        }).when(initialStateMock).execute(gameController);

        // Act
        gameController.startGame();

        // Assert
        verify(initialStateMock, times(1)).execute(gameController);
    }

    @Test
    void givenGameConfig_whenSetGameConfig_thenGameSessionInitialized() {
        // Arrange
        GameConfig config = new GameConfig("Животные", "easy", 5);
        Word wordMock = new Word("кот", "Домашнее животное, которое мурлычет");
        when(wordServiceMock.getRandomWord("Животные", "easy")).thenReturn(wordMock);
        GameSession sessionMock = mock(GameSession.class);
        when(gameServiceMock.createGameSession(wordMock, 5)).thenReturn(sessionMock);

        // Act
        gameController.setGameConfig(config);

        // Assert
        assertThat(gameController.getGameConfig()).isEqualTo(config);
        assertThat(gameController.getGameSession()).isEqualTo(sessionMock);
    }

    @Test
    void whenReset_thenGameControllerResetToInitialState() {
        // Arrange
        gameController.setGameConfig(new GameConfig("Животные", "easy", 5));
        gameController.setGameSession(mock(GameSession.class));
        gameController.setCurrentState(mock(GameState.class));

        // Act
        gameController.reset();

        // Assert
        assertThat(gameController.getGameConfig()).isNull();
        assertThat(gameController.getGameSession()).isNull();
        assertThat(gameController.getCurrentState()).isInstanceOf(StartState.class);
    }
}
