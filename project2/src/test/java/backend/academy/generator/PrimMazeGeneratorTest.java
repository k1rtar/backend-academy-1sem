package backend.academy.generator;

import backend.academy.model.Maze;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class PrimMazeGeneratorTest {

    private PrimMazeGenerator generator;

    @BeforeEach
    void setUp() {
        generator = new PrimMazeGenerator();
    }

    @Test
    @DisplayName("generate() должен корректно генерировать MST для ненулевых размеров")
    void generateShouldInitMazeAndAddEdges() {
        // Arrange
        int height = 5;
        int width = 5;

        // Act
        Maze maze = generator.generate(height, width);

        // Assert
        assertThat(maze).isNotNull();
        assertThat(maze.getHeight()).isEqualTo(height);
        assertThat(maze.getWidth()).isEqualTo(width);
        // Проверим число рёбер
        int v = height*width;
        assertThat(maze.getEdges()).hasSize(v-1);
    }

    @Test
    @DisplayName("generate() должен бросать исключение при некорректных размерах")
    void generateShouldThrowForInvalidSize() {
        assertThatThrownBy(() -> generator.generate(0,0))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
