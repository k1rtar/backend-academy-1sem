package backend.academy.flame.render;

import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.assertj.core.api.Assertions.assertThat;

class RendererFactoryTest {

    @Test
    void shouldCreateSingleThreadedRenderer() {
        Renderer r = RendererFactory.createRenderer(new Random(),1);
        assertThat(r).isInstanceOf(SingleThreadedRenderer.class);
    }

    @Test
    void shouldCreateMultiThreadedRenderer() {
        Renderer r = RendererFactory.createRenderer(new Random(),4);
        assertThat(r).isInstanceOf(MultiThreadedRenderer.class);
    }
}
