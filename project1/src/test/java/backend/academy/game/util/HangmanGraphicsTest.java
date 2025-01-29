package backend.academy.game.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class HangmanGraphicsTest {

    @Test
    void givenValidAttempts_whenGetStage_thenReturnsCorrectStage() {
        // Arrange
        int totalStages = HangmanGraphics.getTotalStages();

        // Act & Assert
        for (int i = 0; i < totalStages; i++) {
            String stage = HangmanGraphics.getStage(i);
            assertThat(stage).isNotNull();
            assertThat(stage).isNotEmpty();
        }
    }

    @Test
    void givenInvalidAttempts_whenGetStage_thenReturnsCorrectStage() {
        // Act
        String negativeStage = HangmanGraphics.getStage(-1);
        String overStage = HangmanGraphics.getStage(100);

        // Assert
        String firstStage = HangmanGraphics.getStage(0);
        String finalStage = HangmanGraphics.getFinalStage();
        assertThat(negativeStage).isEqualTo(firstStage);
        assertThat(overStage).isEqualTo(finalStage);
    }
}
