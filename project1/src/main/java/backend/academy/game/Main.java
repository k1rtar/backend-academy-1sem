package backend.academy.game;

import backend.academy.game.controller.GameController;
import backend.academy.game.repository.WordRepository;
import backend.academy.game.service.WordService;
import backend.academy.game.util.IInputOutput;
import backend.academy.game.util.InputOutput;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

/**
 * Точка входа в приложение.
 */
@Log4j2
@UtilityClass
public class Main {
    public static void main(String[] args) {
        Path wordFilePath = Paths.get("src", "main", "resources", "words.txt");

        if (args.length > 0) {
            wordFilePath = Paths.get(args[0]);
        }

        try (IInputOutput io = new InputOutput(System.in, System.out)) {
            WordRepository wordRepository = new WordRepository();
            WordService wordService = new WordService(wordRepository, wordFilePath.toString());
            GameController gameController = new GameController(io, wordService);
            gameController.startGame();
        } catch (Exception e) {
            log.error("Ошибка при запуске игры: {}", e.getMessage(), e);
        }
    }
}
