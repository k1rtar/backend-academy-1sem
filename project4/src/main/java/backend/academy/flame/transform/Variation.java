package backend.academy.flame.transform;

import backend.academy.flame.model.Point;

/**
 * Абстрактный класс для вариаций трансформаций.
 * Предоставляет методы для вычисления радиуса и угла точки.
 */
public abstract class Variation implements Transform {
    /**
     * Вычисляет радиус (расстояние от начала координат) для точки.
     *
     * @param p точка для вычисления радиуса
     * @return радиус точки
     */
    protected double r(Point p) {
        return Math.sqrt(p.x() * p.x() + p.y() * p.y());
    }

    /**
     * Вычисляет угол (theta) точки в полярных координатах.
     *
     * @param p точка для вычисления угла
     * @return угол точки в радианах
     */
    protected double theta(Point p) {
        return Math.atan2(p.y(), p.x());
    }
}
