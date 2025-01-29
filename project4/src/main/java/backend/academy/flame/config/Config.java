package backend.academy.flame.config;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Класс конфигурации, загружаемый из JSON-файла.
 * Хранит все параметры, необходимые для генерации фрактального пламени.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Config {
    private static final int DEFAULT_SEED = 13377331;
    private static final int DEFAULT_THREADS = 1;
    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 600;
    private static final double DEFAULT_X_BOUND = 2.0;
    private static final double DEFAULT_Y_BOUND = 1.5;
    private static final int DEFAULT_SYMMETRY = 1;
    private static final int DEFAULT_SAMPLES_NUM = 100000;
    private static final int DEFAULT_ITER_PER_SAMPLE = 50;
    private static final double DEFAULT_GAMMA = 1.0;
    private static final String DEFAULT_OUTPUT = "fractal.png";

    private int seed = DEFAULT_SEED;
    private int threads = DEFAULT_THREADS;
    private int width = DEFAULT_WIDTH;
    private int height = DEFAULT_HEIGHT;
    private double xBound = DEFAULT_X_BOUND;
    private double yBound = DEFAULT_Y_BOUND;
    private int symmetry = DEFAULT_SYMMETRY;
    private int samplesNum = DEFAULT_SAMPLES_NUM;
    private int iterPerSample = DEFAULT_ITER_PER_SAMPLE;
    private double gamma = DEFAULT_GAMMA;
    private String output = DEFAULT_OUTPUT;
    private List<VariationName> variations = List.of();
    private List<String> colors = List.of("#ffffff");
}
