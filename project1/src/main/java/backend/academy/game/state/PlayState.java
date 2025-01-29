package backend.academy.game.state;

import backend.academy.game.controller.GameController;
import backend.academy.game.exception.InvalidInputException;
import backend.academy.game.model.GameSession;
import backend.academy.game.service.ValidationService;
import backend.academy.game.util.HangmanGraphics;
import backend.academy.game.util.IInputOutput;
import java.util.Arrays;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Состояние игры, когда игрок пытается угадать слово.
 */
public class PlayState implements GameState {

    private static final Character[] RUSSIAN_ALPHABET = {
        'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и',
        'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т',
        'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь',
        'э', 'ю', 'я'
    };

    private static final String GAME_INTERRUPTED_MESSAGE = "Ввод прерван. Завершение игры.";

    @Override
    public void execute(GameController gameController) {
        IInputOutput io = gameController.getIo();
        GameSession session = gameController.getGameSession();
        ValidationService validationService = gameController.getValidationService();

        boolean isRunning = true;

        while (isRunning && session.getWrongAttempts() < session.getMaxAttempts()) {
            displayGameState(io, session);

            io.print("Введите букву или 'hint' для подсказки, 'exit' для выхода, 'restart' для перезапуска:");

            String input = null;
            try {
                input = io.readLine();
                if (input == null) {
                    io.print(GAME_INTERRUPTED_MESSAGE);
                    gameController.setState(null);
                    break;
                }
                input = input.trim().toLowerCase(Locale.ROOT);
            } catch (NoSuchElementException e) {
                io.print(GAME_INTERRUPTED_MESSAGE);
                gameController.setState(null);
                break;
            }

            try {
                validationService.validateInput(input);

                if ("hint".equalsIgnoreCase(input)) {
                    io.print("Подсказка: " + session.getHint());
                    continue;
                } else if ("exit".equalsIgnoreCase(input)) {
                    io.print("Выход из игры...");
                    gameController.setState(null);
                    isRunning = false;
                    break;
                } else if ("restart".equalsIgnoreCase(input)) {
                    io.print("Перезапуск игры...");
                    gameController.reset();
                    isRunning = false;
                    continue;
                }

                char letter = input.charAt(0);

                if (session.getUsedLetters().contains(letter)) {
                    io.print("Вы уже угадывали эту букву.");
                    continue;
                }

                if (session.getWordToGuess().indexOf(letter) == -1) {
                    session.incrementWrongAttempts();
                    session.addUsedLetter(letter);
                    io.print("Неправильная буква!");
                } else {
                    session.addGuessedLetter(letter);
                    io.print("Правильно!");
                }

                if (session.isWordGuessed()) {
                    break;
                }

            } catch (InvalidInputException e) {
                io.print(e.getMessage());
            }
        }

        if (session.isWordGuessed()) {
            gameController.setState(new WinState());
        } else if (session.getWrongAttempts() >= session.getMaxAttempts()) {
            gameController.setState(new LoseState());
        }
    }


    private void displayGameState(IInputOutput io, GameSession session) {
        StringBuilder displayWord = new StringBuilder();
        for (char c : session.getWordToGuess().toCharArray()) {
            if (session.getGuessedLetters().contains(c)) {
                displayWord.append(c).append(' ');
            } else {
                displayWord.append("_ ");
            }
        }

        io.print("\n" + HangmanGraphics.getStage(session.getWrongAttempts()));
        io.print("Слово: " + displayWord.toString().trim());
        io.print("Использованные буквы: " + getUsedLettersDisplay(session));
        io.print("Неправильные попытки: " + session.getWrongAttempts() + "/" + session.getMaxAttempts());
    }

    private String getUsedLettersDisplay(GameSession session) {
        return Arrays.stream(RUSSIAN_ALPHABET)
            .map(c -> session.getUsedLetters().contains(c) ? c + " " : "_ ")
            .collect(Collectors.joining()).trim();
    }


}
