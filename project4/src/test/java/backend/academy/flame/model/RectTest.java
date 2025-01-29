package backend.academy.flame.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class RectTest {

    @Test
    void shouldCreateRect() {
        // GIVEN & WHEN
        Rect rect = new Rect(0.0, 0.0, 10.0, 5.0);

        // THEN
        assertThat(rect.x()).isEqualTo(0.0);
        assertThat(rect.y()).isEqualTo(0.0);
        assertThat(rect.width()).isEqualTo(10.0);
        assertThat(rect.height()).isEqualTo(5.0);
    }

    @Test
    void shouldContainPointInside() {
        // GIVEN
        Rect rect = new Rect(0.0, 0.0, 10.0, 5.0);
        Point p = new Point(5.0, 2.5);

        // THEN
        assertThat(rect.contains(p)).isTrue();
    }

    @Test
    void shouldNotContainPointOutside() {
        // GIVEN
        Rect rect = new Rect(0.0, 0.0, 10.0, 5.0);
        Point p = new Point(15.0, 2.5);

        // THEN
        assertThat(rect.contains(p)).isFalse();
    }

    @Test
    void shouldContainPointOnBoundary() {
        // GIVEN
        Rect rect = new Rect(0.0, 0.0, 10.0, 5.0);
        Point p = new Point(10.0, 5.0);

        // THEN
        assertThat(rect.contains(p)).isTrue();
    }
}
