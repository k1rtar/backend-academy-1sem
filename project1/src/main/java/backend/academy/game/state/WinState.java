package backend.academy.game.state;

import backend.academy.game.controller.GameController;
import backend.academy.game.util.IInputOutput;

/**
 * Состояние победы.
 */
public class WinState extends EndState {

    @Override
    protected void displayEndMessage(GameController gameController) {
        IInputOutput io = gameController.getIo();
        io.print("\nПоздравляем! Вы угадали слово: " + gameController.getGameSession().getWordToGuess());
    }
}
