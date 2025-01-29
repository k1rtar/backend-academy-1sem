package backend.academy.flame.transform;

import backend.academy.flame.model.Point;

/**
 * Horseshoe:
 * V(x,y) = ((x - y)(x + y)/r, 2xy/r)
 */
public class Horseshoe extends Variation {
    @Override
    public Point apply(Point p) {
        double rr = r(p);
        return new Point(
            ((p.x() - p.y()) * (p.x() + p.y())) / rr,
            (2 * p.x() * p.y()) / rr
        );
    }
}
