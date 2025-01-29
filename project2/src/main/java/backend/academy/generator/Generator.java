package backend.academy.generator;

import backend.academy.model.Maze;

/**
 * Интерфейс для генераторов лабиринтов.
 * Определяет контракт для реализации алгоритмов генерации.
 */
public interface Generator {
    /**
     * Генерирует лабиринт заданных размеров.
     *
     * @param height высота лабиринта
     * @param width  ширина лабиринта
     * @return сгенерированный лабиринт
     */
    Maze generate(int height, int width);
}
