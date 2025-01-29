package backend.academy.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MazeTest {

    @Test
    @DisplayName("Конструктор должен корректно инициализировать свойства лабиринта")
    void constructor_shouldInitializeMazePropertiesCorrectly() {
        // Arrange
        int height = 5;
        int width = 7;

        // Act
        Maze maze = new Maze(height, width);

        // Assert
        assertAll("Инициализация лабиринта",
            () -> assertEquals(height, maze.getHeight(), "Высота лабиринта должна соответствовать заданному значению"),
            () -> assertEquals(width, maze.getWidth(), "Ширина лабиринта должна соответствовать заданному значению"),
            () -> assertNotNull(maze.getGrid(), "Сетка лабиринта не должна быть null"),
            () -> assertEquals(height, maze.getGrid().length, "Сетка должна иметь корректное количество строк"),
            () -> assertEquals(width, maze.getGrid()[0].length, "Сетка должна иметь корректное количество столбцов"),
            () -> assertTrue(maze.getEdges().isEmpty(), "Новый лабиринт не должен содержать рёбер")
        );
    }

    @Test
    @DisplayName("getCell() должен возвращать корректную клетку для валидных координат")
    void getCell_shouldReturnCorrectCell_whenCoordinatesAreValid() {
        // Arrange
        Maze maze = new Maze(3, 3);

        // Act
        Cell cell = maze.getCell(1, 1);

        // Assert
        assertAll("Получение клетки",
            () -> assertNotNull(cell, "Клетка не должна быть null"),
            () -> assertEquals(1, cell.getRow(), "Строка клетки должна быть 1"),
            () -> assertEquals(1, cell.getCol(), "Столбец клетки должен быть 1")
        );
    }

    @Test
    @DisplayName("getCell() должен выбрасывать IndexOutOfBoundsException для невалидных координат")
    void getCell_shouldThrowException_whenCoordinatesAreInvalid() {
        // Arrange
        Maze maze = new Maze(2, 2);

        // Act & Assert
        assertAll("Получение клетки с невалидными координатами",
            () -> assertThrows(IndexOutOfBoundsException.class, () -> maze.getCell(-1, 0),
                "Доступ к клетке с отрицательной строкой должен выбрасывать IndexOutOfBoundsException"),
            () -> assertThrows(IndexOutOfBoundsException.class, () -> maze.getCell(0, -1),
                "Доступ к клетке с отрицательным столбцом должен выбрасывать IndexOutOfBoundsException"),
            () -> assertThrows(IndexOutOfBoundsException.class, () -> maze.getCell(2, 0),
                "Доступ к клетке с строкой, равной высоте, должен выбрасывать IndexOutOfBoundsException"),
            () -> assertThrows(IndexOutOfBoundsException.class, () -> maze.getCell(0, 2),
                "Доступ к клетке с столбцом, равным ширине, должен выбрасывать IndexOutOfBoundsException")
        );
    }

    @Test
    @DisplayName("addEdge() должен корректно добавлять рёбра в лабиринт")
    void addEdge_shouldAddEdgeToMazeCorrectly() {
        // Arrange
        Maze maze = new Maze(2, 2);
        Cell cell1 = maze.getCell(0, 0);
        Cell cell2 = maze.getCell(0, 1);
        Maze.Edge edge = new Maze.Edge(cell1, cell2);

        // Act
        maze.getEdges().add(edge);

        // Assert
        assertTrue(maze.getEdges().contains(edge), "Лабиринт должен содержать добавленное ребро");
    }

    @Test
    @DisplayName("Рёбра, соединяющие одни и те же клетки, должны быть равны и иметь одинаковый hashCode")
    void edgeEquality_shouldRespectCellConnections() {
        // Arrange
        Maze maze = new Maze(2, 2);
        Cell cell1 = maze.getCell(0, 0);
        Cell cell2 = maze.getCell(0, 1);
        Maze.Edge edge1 = new Maze.Edge(cell1, cell2);
        Maze.Edge edge2 = new Maze.Edge(cell2, cell1);

        // Act & Assert
        assertAll("Проверка равенства и hashCode рёбер",
            () -> assertEquals(edge1, edge2, "Рёбра, соединяющие одни и те же клетки, должны быть равны"),
            () -> assertEquals(edge1.hashCode(), edge2.hashCode(), "hashCode должен быть одинаковым для эквивалентных рёбер")
        );
    }
}
