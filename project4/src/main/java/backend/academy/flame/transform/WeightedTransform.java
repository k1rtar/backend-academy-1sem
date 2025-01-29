package backend.academy.flame.transform;

import backend.academy.flame.model.Point;

/**
 * Класс, представляющий взвешенную трансформацию.
 * Применяет трансформацию к точке и масштабирует результат на заданный вес.
 */
public record WeightedTransform(Transform transform, double weight) implements Transform {

    @Override
    public Point apply(Point point) {
        Point p = transform.apply(point);
        return new Point(p.x() * weight, p.y() * weight);
    }
}
