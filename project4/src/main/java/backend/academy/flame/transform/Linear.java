package backend.academy.flame.transform;

import backend.academy.flame.model.Point;

/**
 * Linear: V(x,y) = (x,y)
 */
public class Linear extends Variation {
    @Override
    public Point apply(Point point) {
        return point;
    }
}
