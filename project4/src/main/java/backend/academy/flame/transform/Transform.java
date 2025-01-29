package backend.academy.flame.transform;

import backend.academy.flame.model.Point;
import java.util.function.Function;


/**
 * Интерфейс для определения трансформации точки.
 */
public interface Transform extends Function<Point, Point> {
    @Override
    Point apply(Point point);
}
