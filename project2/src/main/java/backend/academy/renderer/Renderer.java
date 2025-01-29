package backend.academy.renderer;

import backend.academy.model.Coordinate;
import backend.academy.model.Maze;
import java.util.List;

/**
 * Интерфейс для рендереров лабиринта.
 * Определяет методы для отображения лабиринта в различных форматах.
 */
public interface Renderer {
    /**
     * Отображает лабиринт без пути.
     *
     * @param maze лабиринт для отображения
     * @return строковое представление лабиринта
     */
    String render(Maze maze);

    /**
     * Отображает лабиринт с путем.
     *
     * @param maze  лабиринт для отображения
     * @param path  список координат пути
     * @param start начальная точка
     * @param end   конечная точка
     * @return строковое представление лабиринта с путем
     */
    String render(Maze maze, List<Coordinate> path, Coordinate start, Coordinate end);
}
