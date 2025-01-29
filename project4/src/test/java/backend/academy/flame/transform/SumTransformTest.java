package backend.academy.flame.transform;

import backend.academy.flame.model.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class SumTransformTest {

    private Transform inner;
    private Transform t1;
    private Transform t2;
    private SumTransform sumTransform;

    @BeforeEach
    void setUp() {
        inner = mock(Transform.class);
        t1 = mock(Transform.class);
        t2 = mock(Transform.class);

        sumTransform = new SumTransform(inner, List.of(t1,t2));
    }

    @Test
    void shouldSumResults() {
        // GIVEN
        Point p = new Point(1,1);
        when(inner.apply(p)).thenReturn(new Point(2,2));
        when(t1.apply(new Point(2,2))).thenReturn(new Point(3,3));
        when(t2.apply(new Point(2,2))).thenReturn(new Point(1,10));

        // WHEN
        Point result = sumTransform.apply(p);

        // THEN
        // sum of transforms: start from (2,2), t1->(3,3), t2->(1,10)
        // sum: (3+1,3+10)=(4,13)
        assertThat(result.x()).isEqualTo(4);
        assertThat(result.y()).isEqualTo(13);
    }
}
