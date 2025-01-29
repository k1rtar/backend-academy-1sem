package backend.academy.flame.render;

import backend.academy.flame.model.FractalImage;
import backend.academy.flame.model.MyColor;
import backend.academy.flame.model.Pixel;
import backend.academy.flame.model.Rect;
import backend.academy.flame.transform.ColoredTransform;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.List;

class RenderConfigTest {

    @Test
    void shouldCreateRenderConfig() {
        // GIVEN
        FractalImage img = FractalImage.create(800, 600, () -> new Pixel(new MyColor(0, 0, 0), 0));
        Rect world = new Rect(-2.0, -1.5, 4.0, 3.0);
        ColoredTransform ct = mock(ColoredTransform.class);
        List<ColoredTransform> transforms = List.of(ct);
        int samplesNum = 100000;
        int iterPerSample = 50;
        int symmetry = 1;

        // WHEN
        RenderConfig cfg = new RenderConfig(img, world, transforms, samplesNum, iterPerSample, symmetry);

        // THEN
        assertThat(cfg.canvas()).isEqualTo(img);
        assertThat(cfg.world()).isEqualTo(world);
        assertThat(cfg.transforms()).isEqualTo(transforms);
        assertThat(cfg.samplesNum()).isEqualTo(samplesNum);
        assertThat(cfg.iterPerSample()).isEqualTo(iterPerSample);
        assertThat(cfg.symmetry()).isEqualTo(symmetry);
    }
}
