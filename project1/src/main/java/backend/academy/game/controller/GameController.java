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
import lombok.Getter;
import lombok.Setter;

/**
 * Основной контроллер игры, управляющий состояниями и взаимодействующий с сервисами.
 */
@Getter
@Setter
public class GameController {

    private GameState currentState;
    private GameConfig gameConfig;
    private GameSession gameSession;
    private WordService wordService;
    private GameService gameService;
    private ValidationService validationService;
    private IInputOutput io;
    private MessageProvider messageProvider;

    public GameController(IInputOutput io, WordService wordService) {
        this.wordService = wordService;
        this.gameService = new GameService();
        this.validationService = new ValidationService();
        this.io = io;
        this.messageProvider = new MessageProvider();
        this.currentState = new StartState();
    }

    public void startGame() {
        while (currentState != null) {
            currentState.execute(this);
        }
    }

    public void setState(GameState state) {
        this.currentState = state;
    }

    public void setGameConfig(GameConfig config) {
        this.gameConfig = config;
        Word word = wordService.getRandomWord(config.getCategory(), config.getDifficulty());
        this.gameSession = gameService.createGameSession(word, config.getMaxAttempts());
    }

    public void reset() {
        this.gameConfig = null;
        this.gameSession = null;
        this.currentState = new StartState();
    }
}
