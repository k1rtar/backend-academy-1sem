package backend.academy.flame.transform;

import backend.academy.flame.model.Point;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class LinearTest {
    @Test
    void shouldReturnSamePoint() {
        Linear linear = new Linear();
        Point p = new Point(1,2);
        Point result = linear.apply(p);
        assertThat(result).isEqualTo(p);
    }
}
