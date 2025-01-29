package backend.academy.game.util;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.experimental.UtilityClass;

/**
 * Утилитарный класс для работы со стадиями виселицы.
 */
@UtilityClass
public class HangmanGraphics {
    private static final List<String> HANGMAN_STAGES = loadHangmanStages();

    private static List<String> loadHangmanStages() {
        List<String> stages = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
            Objects.requireNonNull(HangmanGraphics.class.getClassLoader().getResourceAsStream("hangman_stages.txt")),
            StandardCharsets.UTF_8))) {
            StringBuilder stageBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                if (line.equals("---")) {
                    stages.add(stageBuilder.toString());
                    stageBuilder.setLength(0);
                } else {
                    stageBuilder.append(line).append(System.lineSeparator());
                }
            }
            if (stageBuilder.length() > 0) {
                stages.add(stageBuilder.toString());
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при загрузке стадий виселицы: " + e.getMessage(), e);
        }
        return stages;
    }

    public static String getStage(int wrongAttempts) {
        if (wrongAttempts < 0) {
            return HANGMAN_STAGES.get(0);
        }
        int index = Math.min(wrongAttempts, HANGMAN_STAGES.size() - 1);
        return HANGMAN_STAGES.get(index);
    }

    public static String getFinalStage() {
        return HANGMAN_STAGES.get(HANGMAN_STAGES.size() - 1);
    }

    public static int getTotalStages() {
        return HANGMAN_STAGES.size();
    }
}
