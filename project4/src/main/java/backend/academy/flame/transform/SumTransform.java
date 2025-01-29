package backend.academy.flame.transform;

import backend.academy.flame.model.Point;
import java.util.List;

/**
 * Класс, представляющий суммарную трансформацию.
 * Применяет внутреннюю трансформацию, а затем суммирует результаты списка трансформаций.
 */
public class SumTransform implements Transform {
    private final Transform innerTransform;
    private final List<Transform> transforms;

    public SumTransform(Transform innerTransform, List<Transform> transforms) {
        this.innerTransform = innerTransform;
        this.transforms = transforms;
    }

    /**
     * Применяет суммарную трансформацию к заданной точке.
     *
     * @param point исходная точка
     * @return трансформированная точка, полученная суммированием результатов
     */
    @Override
    public Point apply(Point point) {
        Point p1 = innerTransform.apply(point);
        double xSum = 0;
        double ySum = 0;
        for (Transform t : transforms) {
            Point p2 = t.apply(p1);
            xSum += p2.x();
            ySum += p2.y();
        }
        return new Point(xSum, ySum);
    }
}
