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

class SingleThreadedRendererTest {
    private SingleThreadedRenderer renderer;
    private RenderConfig config;
    private FractalImage img;
    private Rect world;
    private ColoredTransform ct;

    @BeforeEach
    void setUp() {
        Random rnd = new Random(42);
        renderer = new SingleThreadedRenderer(rnd);

        img = FractalImage.create(10,10, ()->new Pixel(new MyColor(0,0,0),0));
        world = new Rect(0,0,10,10);
        ct = mock(ColoredTransform.class);
        when(ct.apply(any())).thenAnswer(inv->inv.getArgument(0));
        when(ct.myColor()).thenReturn(new MyColor(255,255,255));

        config = new RenderConfig(img,world,List.of(ct),2,2,1);
    }

    @Test
    void shouldRender() {
        renderer.render(config);
        // some pixels should be hit
        long hitCount = 0;
        for (Pixel p : img.data()) {
            hitCount += p.getHitCount();
        }
        assertThat(hitCount).isGreaterThan(0);
    }
}
