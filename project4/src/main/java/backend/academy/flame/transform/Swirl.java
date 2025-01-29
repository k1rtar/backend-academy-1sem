package backend.academy.flame.transform;

import backend.academy.flame.model.Point;

/**
 * Swirl: V(x,y) = (x*sin(r²)-y*cos(r²), x*cos(r²)+y*sin(r²))
 */
public class Swirl extends Variation {
    @Override
    public Point apply(Point point) {
        double r2 = r(point) * r(point);
        return new Point(
            point.x() * Math.sin(r2) - point.y() * Math.cos(r2),
            point.x() * Math.cos(r2) + point.y() * Math.sin(r2)
        );
    }
}
