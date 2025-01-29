package backend.academy.testutils;

import backend.academy.model.Coordinate;
import backend.academy.model.Maze;

import java.util.ArrayList;
import java.util.List;

public class MazeTestUtils {

    /**
     * Проверяет, что лабиринт полностью связен.
     *
     * @param maze лабиринт
     * @return true, если лабиринт связен, иначе false
     */
    public static boolean isMazeConnected(Maze maze) {
        int height = maze.getHeight();
        int width = maze.getWidth();
        boolean[][] visited = new boolean[height][width];
        if (height == 0 || width == 0) return false;

        // Начинаем обход с первой ячейки
        dfs(maze, 0, 0, visited);

        // Проверяем, что все ячейки были посещены
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (!visited[row][col]) return false;
            }
        }
        return true;
    }

    /**
     * Проверяет, что в лабиринте нет циклов.
     *
     * @param maze лабиринт
     * @return true, если циклов нет, иначе false
     */
    public static boolean hasNoCycles(Maze maze) {
        int height = maze.getHeight();
        int width = maze.getWidth();
        boolean[][] visited = new boolean[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (!visited[row][col]) {
                    if (dfsDetectCycle(maze, row, col, -1, -1, visited)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Обход в глубину для проверки связности лабиринта.
     */
    private static void dfs(Maze maze, int row, int col, boolean[][] visited) {
        visited[row][col] = true;
        for (Coordinate neighbor : getNeighbors(maze, row, col)) {
            if (!visited[neighbor.getRow()][neighbor.getCol()]) {
                dfs(maze, neighbor.getRow(), neighbor.getCol(), visited);
            }
        }
    }

    /**
     * Обход в глубину для обнаружения циклов.
     */
    private static boolean dfsDetectCycle(Maze maze, int row, int col, int parentRow, int parentCol, boolean[][] visited) {
        visited[row][col] = true;
        for (Coordinate neighbor : getNeighbors(maze, row, col)) {
            if (!visited[neighbor.getRow()][neighbor.getCol()]) {
                if (dfsDetectCycle(maze, neighbor.getRow(), neighbor.getCol(), row, col, visited)) {
                    return true;
                }
            } else if (neighbor.getRow() != parentRow || neighbor.getCol() != parentCol) {
                return true;
            }
        }
        return false;
    }

    /**
     * Получает соседние координаты для проверки связности и циклов.
     */
    private static List<Coordinate> getNeighbors(Maze maze, int row, int col) {
        List<Coordinate> neighbors = new ArrayList<>();
        for (Maze.Edge edge : maze.getEdges()) {
            if (edge.getCell1().getRow() == row && edge.getCell1().getCol() == col) {
                neighbors.add(new Coordinate(edge.getCell2().getRow(), edge.getCell2().getCol()));
            } else if (edge.getCell2().getRow() == row && edge.getCell2().getCol() == col) {
                neighbors.add(new Coordinate(edge.getCell1().getRow(), edge.getCell1().getCol()));
            }
        }
        return neighbors;
    }

    /**
     * Проверяет, что путь проходит только через рёбра лабиринта.
     *
     * @param maze лабиринт
     * @param path список координат пути
     * @return true, если путь валиден, иначе false
     */
    public static boolean isPathValid(Maze maze, List<Coordinate> path) {
        if (path == null || path.isEmpty()) return false;
        for (int i = 0; i < path.size() - 1; i++) {
            Coordinate current = path.get(i);
            Coordinate next = path.get(i + 1);
            boolean connected = false;
            for (Maze.Edge edge : maze.getEdges()) {
                if ((edge.getCell1().getRow() == current.getRow() && edge.getCell1().getCol() == current.getCol()
                    && edge.getCell2().getRow() == next.getRow() && edge.getCell2().getCol() == next.getCol()) ||
                    (edge.getCell2().getRow() == current.getRow() && edge.getCell2().getCol() == current.getCol()
                        && edge.getCell1().getRow() == next.getRow() && edge.getCell1().getCol() == next.getCol())) {
                    connected = true;
                    break;
                }
            }
            if (!connected) return false;
        }
        return true;
    }
}
