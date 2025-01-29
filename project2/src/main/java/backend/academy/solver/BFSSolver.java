package backend.academy.solver;

import backend.academy.model.Cell;
import backend.academy.model.Coordinate;
import backend.academy.model.Maze;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Реализация алгоритма поиска в ширину (BFS) для решения лабиринта.
 */
public class BFSSolver extends AbstractSolver {

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        boolean[][] visited = new boolean[maze.getHeight()][maze.getWidth()];
        Map<Coordinate, Coordinate> prev = new HashMap<>();
        Queue<Coordinate> queue = new ArrayDeque<>();

        queue.add(start);
        visited[start.getRow()][start.getCol()] = true;

        while (!queue.isEmpty()) {
            Coordinate current = queue.poll();
            Cell cell = maze.getCell(current.getRow(), current.getCol());

            if (current.equals(end)) {
                return reconstructPath(prev, end);
            }

            for (Coordinate neighborCoord : getNeighbors(maze, cell)) {
                int nRow = neighborCoord.getRow();
                int nCol = neighborCoord.getCol();
                if (!visited[nRow][nCol]) {
                    visited[nRow][nCol] = true;
                    prev.put(neighborCoord, current);
                    queue.add(neighborCoord);
                }
            }
        }

        return null;
    }
}
