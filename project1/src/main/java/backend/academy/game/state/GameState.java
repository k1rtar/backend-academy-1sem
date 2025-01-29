package backend.academy.game.state;

import backend.academy.game.controller.GameController;

/**
 * Интерфейс для состояний игры.
 */
public interface GameState {
    void execute(GameController gameController);
}
