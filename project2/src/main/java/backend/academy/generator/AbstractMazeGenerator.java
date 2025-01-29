package backend.academy.generator;

import backend.academy.model.Cell;
import backend.academy.model.Coordinate;
import backend.academy.model.Maze;
import java.util.ArrayList;
import java.util.List;

/**
 * Абстрактный класс для генераторов лабиринтов.
 * Содержит общие методы для алгоритмов генерации лабиринтов.
 */
public abstract class AbstractMazeGenerator implements Generator {

    protected static final int DIRECTIONS = 4;
    protected static final int[] DROW = {-1, 1, 0, 0};
    protected static final int[] DCOL = {0, 0, -1, 1};

    /**
     * Удаляет стену между двумя ячейками путем добавления рёбра в лабиринт.
     *
     * @param cell1 первая ячейка
     * @param cell2 вторая ячейка
     * @param maze  лабиринт, в который добавляется ребро
     */
    protected void removeWallBetweenCells(Cell cell1, Cell cell2, Maze maze) {
        // Добавляем рёбра между cell1 и cell2 в лабиринт
        maze.getEdges().add(new Maze.Edge(cell1, cell2));
    }

    /**
     * Инициализирует сетку лабиринта.
     *
     * @param maze лабиринт
     */
    protected void initializeGrid(Maze maze) {
        int height = maze.getHeight();
        int width = maze.getWidth();
        Cell[][] grid = maze.getGrid();

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                grid[row][col].setRow(row);
                grid[row][col].setCol(col);
            }
        }
    }

    /**
     * Получает соседние клетки, доступные для перемещения.
     *
     * @param maze лабиринт
     * @param cell текущая клетка
     * @return список координат соседних клеток
     */
    protected List<Coordinate> getNeighbors(Maze maze, Cell cell) {
        List<Coordinate> neighbors = new ArrayList<>();
        int row = cell.getRow();
        int col = cell.getCol();

        for (int i = 0; i < DIRECTIONS; i++) {
            int nRow = DROW[i];
            int nCol = DCOL[i];
            int neighborRow = row + nRow;
            int neighborCol = col + nCol;

            if (neighborRow >= 0 && neighborRow < maze.getHeight()
                && neighborCol >= 0 && neighborCol < maze.getWidth()) {
                neighbors.add(new Coordinate(neighborRow, neighborCol));
            }
        }

        return neighbors;
    }
}
