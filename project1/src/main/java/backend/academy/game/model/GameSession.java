package backend.academy.game.model;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;

/**
 * Хранит текущее состояние игровой сессии.
 */
@Getter
public class GameSession {
    private final String wordToGuess;
    private final String hint;
    private final Set<Character> guessedLetters;
    private final Set<Character> usedLetters;
    private int wrongAttempts;
    private final int maxAttempts;

    public GameSession(String wordToGuess, String hint, int maxAttempts) {
        this.wordToGuess = wordToGuess.toLowerCase();
        this.hint = hint;
        this.maxAttempts = maxAttempts;
        this.guessedLetters = new HashSet<>();
        this.usedLetters = new HashSet<>();
        this.wrongAttempts = 0;
    }

    public void incrementWrongAttempts() {
        wrongAttempts++;
    }

    public void addGuessedLetter(char letter) {
        guessedLetters.add(letter);
        usedLetters.add(letter);
    }

    public void addUsedLetter(char letter) {
        usedLetters.add(letter);
    }

    public boolean isWordGuessed() {
        for (char c : wordToGuess.toCharArray()) {
            if (!guessedLetters.contains(c)) {
                return false;
            }
        }
        return true;
    }
}
