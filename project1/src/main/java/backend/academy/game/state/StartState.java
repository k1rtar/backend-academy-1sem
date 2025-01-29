package backend.academy.game.state;

import backend.academy.game.controller.GameController;
import backend.academy.game.model.GameConfig;
import backend.academy.game.service.WordService;
import backend.academy.game.util.IInputOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;

/**
 * Начальное состояние игры.
 */
public class StartState implements GameState {

    private static final String GAME_INTERRUPTED_MESSAGE = "Ввод прерван. Завершение игры.";
    private final Random random = new Random();

    @Override
    public void execute(GameController gameController) {
        IInputOutput io = gameController.getIo();
        WordService wordService = gameController.getWordService();

        io.print(gameController.getMessageProvider().getMessage("welcome"));

        Set<String> categorySet = wordService.getAvailableCategories();
        List<String> categoryList = new ArrayList<>(categorySet);

        io.print("Доступные категории: " + String.join(", ", categoryList));
        io.print("Выберите категорию или нажмите Enter для случайной:");

        String category = readInput(io, categoryList, "категорию");
        boolean continueGame = category != null;

        if (continueGame) {
            Set<String> difficultySet = wordService.getAvailableDifficulties(category);
            List<String> difficultyList = new ArrayList<>(difficultySet);

            io.print("Доступные уровни сложности: " + String.join(", ", difficultyList));
            io.print("Выберите уровень сложности или нажмите Enter для случайного:");

            String difficulty = readInput(io, difficultyList, "сложность");
            continueGame = difficulty != null;

            if (continueGame) {
                int maxAttempts = gameController.getGameService().getDefaultAttempts(difficulty);
                gameController.setGameConfig(new GameConfig(category, difficulty, maxAttempts));
                gameController.setState(new PlayState());
            }
        }

        if (!continueGame) {
            io.print(GAME_INTERRUPTED_MESSAGE);
            gameController.setState(null);
        }
    }

    /**
     * Метод для чтения пользовательского ввода с проверкой.
     */
    private String readInput(IInputOutput io, List<String> options, String optionType) {
        try {
            String input = io.readLine();
            if (input == null) {
                return null;
            }
            input = input.trim();
            String option = getMatchingOption(input, options);

            if (option == null) {
                option = getRandomElement(options);
                io.print("Выбрана случайная " + optionType + ": " + option);
            }

            return option;

        } catch (NoSuchElementException e) {
            return null;
        }
    }

    private String getMatchingOption(String input, List<String> options) {
        if (input == null || input.isEmpty()) {
            return null;
        }
        for (String option : options) {
            if (option.equalsIgnoreCase(input)) {
                return option;
            }
        }
        return null;
    }

    private String getRandomElement(List<String> options) {
        int index = random.nextInt(options.size());
        return options.get(index);
    }
}
