package backend.academy.game.service;

import backend.academy.game.model.GameSession;
import backend.academy.game.model.Word;

/**
 * Содержит бизнес-логику игры.
 */
public class GameService {

    private static final int EASY_ATTEMPTS = 10;
    private static final int MEDIUM_ATTEMPTS = 7;
    private static final int HARD_ATTEMPTS = 5;
    private static final int DEFAULT_ATTEMPTS = MEDIUM_ATTEMPTS;

    public GameSession createGameSession(Word word, int maxAttempts) {
        return new GameSession(word.getWord(), word.getHint(), maxAttempts);
    }

    public int getDefaultAttempts(String difficulty) {
        return switch (difficulty.toLowerCase()) {
            case "easy" -> EASY_ATTEMPTS;
            case "medium" -> MEDIUM_ATTEMPTS;
            case "hard" -> HARD_ATTEMPTS;
            default -> DEFAULT_ATTEMPTS;
        };
    }
}
