package backend.academy.game.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Хранит конфигурацию игры.
 */
@Getter
@AllArgsConstructor
public class GameConfig {
    private final String category;
    private final String difficulty;
    private final int maxAttempts;
}
