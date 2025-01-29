package backend.academy.solver;

import backend.academy.model.Coordinate;
import backend.academy.model.Maze;
import java.util.List;

/**
 * Интерфейс для решателей лабиринтов.
 * Определяет метод для поиска пути в лабиринте.
 */
public interface Solver {
    /**
     * Ищет путь от начальной до конечной точки в лабиринте.
     *
     * @param maze  лабиринт для решения
     * @param start начальная координата
     * @param end   конечная координата
     * @return список координат пути или null, если путь не найден
     */
    List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end);
}
