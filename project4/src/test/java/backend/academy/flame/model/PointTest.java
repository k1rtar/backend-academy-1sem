package backend.academy.flame.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class PointTest {

    @Test
    void shouldCreatePoint() {
        // GIVEN & WHEN
        Point p = new Point(3.5, -2.5);

        // THEN
        assertThat(p.x()).isEqualTo(3.5);
        assertThat(p.y()).isEqualTo(-2.5);
    }

    @Test
    void pointsWithSameCoordinatesShouldBeEqual() {
        // GIVEN
        Point p1 = new Point(1.0, 1.0);
        Point p2 = new Point(1.0, 1.0);

        // THEN
        assertThat(p1).isEqualTo(p2);
    }

    @Test
    void pointsWithDifferentCoordinatesShouldNotBeEqual() {
        // GIVEN
        Point p1 = new Point(1.0, 1.0);
        Point p2 = new Point(2.0, 2.0);

        // THEN
        assertThat(p1).isNotEqualTo(p2);
    }
}
