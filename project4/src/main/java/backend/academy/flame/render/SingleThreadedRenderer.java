package backend.academy.flame.render;

import backend.academy.flame.model.FractalImage;
import backend.academy.flame.model.Point;
import backend.academy.flame.model.Rect;
import backend.academy.flame.transform.ColoredTransform;
import java.util.List;
import java.util.Random;

/**
 * Класс для однопоточного рендеринга фрактального пламени.
 * Выполняет все итерации в текущем потоке без использования параллелизма.
 */
public class SingleThreadedRenderer implements Renderer {

    private final Random random;

    public SingleThreadedRenderer(Random random) {
        this.random = random;
    }

    /**
     * Выполняет рендеринг фрактального пламени в однопоточном режиме.
     *
     * @param cfg конфигурация рендера
     */
    @Override
    public void render(RenderConfig cfg) {
        FractalImage canvas = cfg.canvas();
        Rect world = cfg.world();
        List<ColoredTransform> transforms = cfg.transforms();

        int samples = cfg.samplesNum();
        int iter = cfg.iterPerSample();
        int symmetry = cfg.symmetry();

        for (int num = 0; num < samples; num++) {
            Point p = RenderUtils.randomPoint(world, random);
            for (int i = 0; i < iter; i++) {
                p = RenderUtils.applyTransformAndSymmetry(canvas, world, transforms, symmetry, random, p);
            }
        }
    }
}
