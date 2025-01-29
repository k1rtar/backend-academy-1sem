package backend.academy.renderer;

import backend.academy.model.Coordinate;
import backend.academy.model.Maze;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleRendererTest {

    @Test
    @DisplayName("render() должен возвращать непустую строку для лабиринта без пути")
    void render_shouldReturnNonEmptyOutput_whenMazeWithoutPath() {
        // Arrange
        Maze maze = new Maze(2, 2);
        Renderer renderer = new ConsoleRenderer();

        // Act
        String output = renderer.render(maze);

        // Assert
        assertAll("Рендеринг лабиринта без пути",
            () -> assertNotNull(output, "Вывод не должен быть null"),
            () -> assertFalse(output.isEmpty(), "Вывод не должен быть пустым")
            // Дополнительно: Проверка структуры вывода, если требуется
        );
    }

    @Test
    @DisplayName("render() должен возвращать непустую строку для лабиринта с путём")
    void render_shouldReturnNonEmptyOutput_whenMazeWithPath() {
        // Arrange
        Maze maze = new Maze(2, 2);
        Renderer renderer = new ConsoleRenderer();
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(1, 1);
        List<Coordinate> path = Arrays.asList(
            new Coordinate(0, 0),
            new Coordinate(0, 1),
            new Coordinate(1, 1)
        );

        // Act
        String output = renderer.render(maze, path, start, end);

        // Assert
        assertAll("Рендеринг лабиринта с путём",
            () -> assertNotNull(output, "Вывод не должен быть null"),
            () -> assertFalse(output.isEmpty(), "Вывод не должен быть пустым"),
            () -> assertTrue(output.contains("A"), "Вывод должен содержать стартовую точку 'A'"),
            () -> assertTrue(output.contains("B"), "Вывод должен содержать конечную точку 'B'"),
            () -> assertTrue(output.contains("*"), "Вывод должен содержать маркеры пути '*'")
        );
    }

    @Test
    @DisplayName("render() должен выбрасывать NullPointerException при передаче null лабиринта")
    void render_shouldThrowException_whenMazeIsNull() {
        // Arrange
        Renderer renderer = new ConsoleRenderer();

        // Act & Assert
        assertThrows(NullPointerException.class, () -> renderer.render(null),
            "Передача null лабиринта должна выбрасывать NullPointerException");
    }

    @Test
    @DisplayName("render() должен корректно обрабатывать невалидные координаты без выброса исключений")
    void render_shouldHandleInvalidCoordinatesGracefully() {
        // Arrange
        Maze maze = new Maze(2, 2);
        Renderer renderer = new ConsoleRenderer();
        Coordinate start = new Coordinate(2, 2); // Невалидная координата
        Coordinate end = new Coordinate(1, 1);

        // Act & Assert
        assertDoesNotThrow(() -> renderer.render(maze, null, start, end),
            "Рендеринг с невалидными координатами не должен выбрасывать исключения");
    }
}
