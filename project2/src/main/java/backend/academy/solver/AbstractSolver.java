package backend.academy.solver;

import backend.academy.model.Cell;
import backend.academy.model.Coordinate;
import backend.academy.model.Maze;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Абстрактный класс для решателей лабиринтов.
 * Содержит общие методы для алгоритмов решения лабиринта.
 */
public abstract class AbstractSolver implements Solver {

    /**
     * Восстанавливает путь от начальной до конечной точки.
     *
     * @param prev карта предыдущих координат
     * @param end  конечная координата
     * @return список координат пути
     */
    protected List<Coordinate> reconstructPath(Map<Coordinate, Coordinate> prev, Coordinate end) {
        List<Coordinate> path = new ArrayList<>();
        Coordinate at = end;
        while (at != null) {
            path.add(0, at);
            at = prev.get(at);
        }
        return path;
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

        // Проход по всем рёбрам лабиринта и поиск соседей
        for (Maze.Edge edge : maze.getEdges()) {
            if (edge.getCell1().equals(cell)) {
                neighbors.add(new Coordinate(edge.getCell2().getRow(), edge.getCell2().getCol()));
            } else if (edge.getCell2().equals(cell)) {
                neighbors.add(new Coordinate(edge.getCell1().getRow(), edge.getCell1().getCol()));
            }
        }

        return neighbors;
    }
}
