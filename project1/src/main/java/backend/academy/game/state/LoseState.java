package backend.academy.game.state;

import backend.academy.game.controller.GameController;
import backend.academy.game.util.HangmanGraphics;
import backend.academy.game.util.IInputOutput;

/**
 * Состояние проигрыша.
 */
public class LoseState extends EndState {

    @Override
    protected void displayEndMessage(GameController gameController) {
        IInputOutput io = gameController.getIo();
        String finalStage = HangmanGraphics.getFinalStage();
        io.print("\n" + finalStage);

        io.print("\nВы проиграли! Загаданное слово было: " + gameController.getGameSession().getWordToGuess());
    }
}
