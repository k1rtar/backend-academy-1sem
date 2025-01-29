package backend.academy.game.state;

import backend.academy.game.controller.GameController;
import backend.academy.game.model.GameConfig;
import backend.academy.game.service.GameService;
import backend.academy.game.service.WordService;
import backend.academy.game.util.IInputOutput;
import backend.academy.game.util.MessageProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StartStateTest {

    @Mock
    private IInputOutput ioMock;
    @Mock
    private WordService wordServiceMock;
    @Mock
    private GameService gameServiceMock;
    @Mock
    private MessageProvider messageProviderMock;

    @InjectMocks
    private GameController gameController;

    private StartState startState;

    @BeforeEach
    void setUp() {
        gameController.setGameService(gameServiceMock);
        gameController.setMessageProvider(messageProviderMock);

        startState = new StartState();
    }

    @Test
    void givenValidCategoryAndDifficulty_whenExecute_thenGameConfigSet() {
        // Arrange
        Set<String> categories = Set.of("Животные", "Фрукты");
        Set<String> difficulties = Set.of("easy", "hard");
        when(wordServiceMock.getAvailableCategories()).thenReturn(categories);
        when(ioMock.readLine()).thenReturn("Животные", "easy");
        when(wordServiceMock.getAvailableDifficulties("Животные")).thenReturn(difficulties);
        when(gameServiceMock.getDefaultAttempts("easy")).thenReturn(5);

        // Act
        startState.execute(gameController);

        // Assert
        GameConfig config = gameController.getGameConfig();
        assertThat(config.getCategory()).isEqualTo("Животные");
        assertThat(config.getDifficulty()).isEqualTo("easy");
        assertThat(config.getMaxAttempts()).isEqualTo(5);
        assertThat(gameController.getCurrentState()).isInstanceOf(PlayState.class);
    }

    @Test
    void givenEmptyInputs_whenExecute_thenRandomCategoryAndDifficultySelected() {
        // Arrange
        Set<String> categories = Set.of("Животные", "Фрукты");
        Set<String> difficulties = Set.of("easy", "hard");
        when(wordServiceMock.getAvailableCategories()).thenReturn(categories);
        when(ioMock.readLine()).thenReturn("", "");
        when(wordServiceMock.getAvailableDifficulties(anyString())).thenReturn(difficulties);
        when(gameServiceMock.getDefaultAttempts(anyString())).thenReturn(7);

        // Act
        startState.execute(gameController);

        // Assert
        GameConfig config = gameController.getGameConfig();
        assertThat(categories).contains(config.getCategory());
        assertThat(difficulties).contains(config.getDifficulty());
        assertThat(gameController.getCurrentState()).isInstanceOf(PlayState.class);
    }
}
