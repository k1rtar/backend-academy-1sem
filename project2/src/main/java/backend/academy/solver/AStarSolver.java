package backend.academy.solver;

import backend.academy.model.Cell;
import backend.academy.model.Coordinate;
import backend.academy.model.Maze;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Реализация алгоритма A* для решения лабиринта.
 */
public class AStarSolver extends AbstractSolver {

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        int height = maze.getHeight();
        int width = maze.getWidth();

        boolean[][] visited = new boolean[height][width];
        Map<Coordinate, Coordinate> prev = new HashMap<>();
        Map<Coordinate, Integer> gScore = new HashMap<>();
        Map<Coordinate, Integer> fScore = new HashMap<>();


        PriorityQueue<Coordinate> openSet = new PriorityQueue<>(Comparator.comparingInt(fScore::get));

        gScore.put(start, 0);
        fScore.put(start, heuristic(start, end));
        openSet.add(start);

        while (!openSet.isEmpty()) {
            Coordinate current = openSet.poll();

            if (current.equals(end)) {
                return reconstructPath(prev, end);
            }

            visited[current.getRow()][current.getCol()] = true;

            Cell cell = maze.getCell(current.getRow(), current.getCol());
            for (Coordinate neighborCoord : getNeighbors(maze, cell)) {
                if (visited[neighborCoord.getRow()][neighborCoord.getCol()]) {
                    continue;
                }

                int tentativeGScore = gScore.getOrDefault(current, Integer.MAX_VALUE) + 1;


                if (tentativeGScore < gScore.getOrDefault(neighborCoord, Integer.MAX_VALUE)) {
                    prev.put(neighborCoord, current);
                    gScore.put(neighborCoord, tentativeGScore);
                    fScore.put(neighborCoord, tentativeGScore + heuristic(neighborCoord, end));

                    if (!openSet.contains(neighborCoord)) {
                        openSet.add(neighborCoord);
                    } else {
                        openSet.remove(neighborCoord);
                        openSet.add(neighborCoord);
                    }
                }
            }
        }

        return null;
    }

    /**
     * Эвристическая функция, вычисляющая Манхэттенское расстояние между двумя координатами.
     *
     * @param a первая координата
     * @param b вторая координата
     * @return Манхэттенское расстояние между a и b
     */
    private int heuristic(Coordinate a, Coordinate b) {
        return Math.abs(a.getRow() - b.getRow()) + Math.abs(a.getCol() - b.getCol());
    }
}
