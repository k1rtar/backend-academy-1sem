package backend.academy.flame.transform;

import backend.academy.flame.model.MyColor;
import backend.academy.flame.model.Point;

/**
 * Класс, представляющий трансформацию с привязанным к ней цветом.
 * Используется для определения цвета при применении трансформации.
 */
public record ColoredTransform(Transform transform, MyColor myColor) implements Transform {

    /**
     * Применяет трансформацию к точке.
     *
     * @param point исходная точка
     * @return трансформированная точка
     */
    @Override
    public Point apply(Point point) {
        return transform.apply(point);
    }
}
