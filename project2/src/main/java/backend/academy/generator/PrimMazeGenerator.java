package backend.academy.generator;

import backend.academy.model.Cell;
import backend.academy.model.Maze;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;

/**
 * Реализация генератора лабиринтов с использованием алгоритма Прима.
 */
public class PrimMazeGenerator extends AbstractMazeGenerator {

    private static final Random RANDOM = new Random();

    @Override
    public Maze generate(int height, int width) {
        if (height <= 0 || width <= 0) {
            throw new IllegalArgumentException("Height and width must be greater than 0");
        }

        Maze maze = new Maze(height, width);
        initializeGrid(maze);

        int startRow = RANDOM.nextInt(height);
        int startCol = RANDOM.nextInt(width);
        Cell startCell = maze.getCell(startRow, startCol);

        Set<Cell> visited = new HashSet<>();
        visited.add(startCell);

        PriorityQueue<EdgeWithPriority> walls = new PriorityQueue<>();
        addWalls(maze.getGrid(), startCell, walls, visited);

        while (!walls.isEmpty()) {
            EdgeWithPriority edgeWithPriority = walls.poll();
            Maze.Edge edge = edgeWithPriority.edge;

            Cell nextCell = edge.getCell2();

            if (!visited.contains(nextCell)) {
                removeWallBetweenCells(edge.getCell1(), edge.getCell2(), maze);
                visited.add(nextCell);
                addWalls(maze.getGrid(), nextCell, walls, visited);
            }
        }

        return maze;
    }

    /**
     * Добавляет все возможные стены из данной ячейки в очередь стен с присвоенными случайными приоритетами.
     *
     * @param grid    сетка ячеек лабиринта
     * @param cell    текущая ячейка
     * @param walls   очередь стен с приоритетами
     * @param visited множество посещённых ячеек
     */
    private void addWalls(Cell[][] grid, Cell cell, PriorityQueue<EdgeWithPriority> walls, Set<Cell> visited) {
        int row = cell.getRow();
        int col = cell.getCol();
        int height = grid.length;
        int width = grid[0].length;

        for (int i = 0; i < DIRECTIONS; i++) {
            int nRow = DROW[i];
            int nCol = DCOL[i];
            int neighborRow = row + nRow;
            int neighborCol = col + nCol;

            if (neighborRow >= 0 && neighborRow < height && neighborCol >= 0 && neighborCol < width) {
                Cell neighbor = grid[neighborRow][neighborCol];

                if (!visited.contains(neighbor)) {
                    Maze.Edge edge = new Maze.Edge(cell, neighbor);
                    double priority = RANDOM.nextDouble();
                    walls.add(new EdgeWithPriority(edge, priority));
                }
            }
        }
    }

    /**
     * Внутренний класс для представления рёбер с приоритетом.
     */
    private static class EdgeWithPriority implements Comparable<EdgeWithPriority> {
        private final Maze.Edge edge;
        private final double priority;

        EdgeWithPriority(Maze.Edge edge, double priority) {
            this.edge = edge;
            this.priority = priority;
        }

        @Override
        public int compareTo(EdgeWithPriority o) {
            if (o == null) {
                throw new NullPointerException("Cannot compare to null EdgeWithPriority");
            }
            return Double.compare(this.priority, o.priority);
        }
    }
}
