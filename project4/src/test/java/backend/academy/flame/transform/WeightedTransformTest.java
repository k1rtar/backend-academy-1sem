package backend.academy.flame.transform;

import backend.academy.flame.model.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class WeightedTransformTest {

    private Transform inner;
    private WeightedTransform wt;

    @BeforeEach
    void setUp() {
        inner = mock(Transform.class);
        wt = new WeightedTransform(inner, 2.0);
    }

    @Test
    void shouldApplyWeightedTransform() {
        // GIVEN
        Point p = new Point(1,1);
        when(inner.apply(p)).thenReturn(new Point(10,10));

        // WHEN
        Point result = wt.apply(p);

        // THEN
        // weight=2.0 => result = (10*2, 10*2) = (20,20)
        assertThat(result.x()).isEqualTo(20);
        assertThat(result.y()).isEqualTo(20);
        verify(inner).apply(p);
    }
}
