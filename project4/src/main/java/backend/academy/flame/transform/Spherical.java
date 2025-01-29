package backend.academy.flame.transform;

import backend.academy.flame.model.Point;

/**
 * Spherical: V(x,y) = (x/r², y/r²)
 */
public class Spherical extends Variation {
    @Override
    public Point apply(Point point) {
        double rr = r(point);
        double r2 = rr * rr;
        return new Point(point.x() / r2, point.y() / r2);
    }
}
