package backend.academy.game.state;

import backend.academy.game.controller.GameController;
import backend.academy.game.util.IInputOutput;

/**
 * Абстрактный класс для конечных состояний игры (победа или поражение).
 */
public abstract class EndState implements GameState {

    @Override
    public void execute(GameController gameController) {
        IInputOutput io = gameController.getIo();
        displayEndMessage(gameController);
        askForReplay(gameController, io);
    }

    protected abstract void displayEndMessage(GameController gameController);

    protected void askForReplay(GameController gameController, IInputOutput io) {
        io.print("Хотите сыграть ещё раз? (y/n):");
        String input = io.readLine();

        if (input != null) {
            input = input.trim().toLowerCase();
        }

        if ("y".equals(input)) {
            gameController.reset();
        } else {
            io.print("Спасибо за игру!");
            gameController.setState(null);
        }
    }

}
