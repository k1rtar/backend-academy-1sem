package backend.academy.game.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Представляет слово и его подсказку.
 */
@Getter
@AllArgsConstructor
public class Word {
    private final String word;
    private final String hint;
}
