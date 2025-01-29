package backend.academy.generator;

import backend.academy.model.Maze;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;


class KruskalMazeGeneratorTest {

    @Test
    @DisplayName("generate() должен генерировать лабиринт корректных размеров")
    void generateShouldCreateMazeWithCorrectSize() {
        // Arrange
        KruskalMazeGenerator generator = new KruskalMazeGenerator();
        int height = 5;
        int width = 5;

        // Act
        Maze maze = generator.generate(height, width);

        // Assert
        assertThat(maze).isNotNull();
        assertThat(maze.getHeight()).isEqualTo(height);
        assertThat(maze.getWidth()).isEqualTo(width);
        // Проверим, что число рёбер разумно для MST
        // Для MST с V узлами рёбер будет V-1
        int v = height * width;
        assertThat(maze.getEdges().size()).isEqualTo(v - 1);
    }

    @Test
    @DisplayName("generate() должен выбрасывать IllegalArgumentException при нулевых размерах")
    void generateShouldThrowExceptionForZeroSize() {
        // Arrange
        KruskalMazeGenerator generator = new KruskalMazeGenerator();

        // Act & Assert
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
            () -> generator.generate(0,0));
    }
}
