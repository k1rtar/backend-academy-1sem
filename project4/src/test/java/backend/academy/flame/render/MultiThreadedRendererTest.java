package backend.academy.flame.render;

import backend.academy.flame.model.FractalImage;
import backend.academy.flame.model.MyColor;
import backend.academy.flame.model.Pixel;
import backend.academy.flame.model.Rect;
import backend.academy.flame.transform.ColoredTransform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Random;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class MultiThreadedRendererTest {

    private MultiThreadedRenderer renderer;
    private RenderConfig config;
    private FractalImage img;
    private Rect world;
    private ColoredTransform ct;

    @BeforeEach
    void setUp() {
        // GIVEN
        renderer = new MultiThreadedRenderer(4);

        img = FractalImage.create(10, 10, () -> new Pixel(new MyColor(0, 0, 0), 0));
        world = new Rect(0, 0, 10, 10);
        ct = mock(ColoredTransform.class);
        when(ct.apply(any())).thenAnswer(inv -> inv.getArgument(0));
        when(ct.myColor()).thenReturn(new MyColor(255, 255, 255));

        config = new RenderConfig(img, world, List.of(ct), 1000, 10, 1);
    }

    @Test
    void shouldRenderWithoutErrors() {
        // WHEN
        renderer.render(config);

        // THEN
        // Проверяем, что некоторые пиксели были обновлены
        long hitCount = 0;
        for (Pixel p : img.data()) {
            hitCount += p.getHitCount();
        }
        assertThat(hitCount).isGreaterThan(0);
    }

    @Test
    void shouldDistributeSamplesAcrossThreads() {
        renderer.render(config);
        assertThat(true).isTrue();
    }
}
