package backend.academy.flame.render;

import backend.academy.flame.model.FractalImage;
import backend.academy.flame.model.Point;
import backend.academy.flame.model.Rect;
import backend.academy.flame.transform.ColoredTransform;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * Класс для многопоточного рендеринга фрактального пламени.
 * Делит общее количество семплов между несколькими потоками для параллельной обработки.
 */
public class MultiThreadedRenderer implements Renderer {

    private final int threads;

    /**
     * Конструктор для инициализации многопоточного рендерера.
     * @param threads количество потоков
     */
    public MultiThreadedRenderer(int threads) {
        this.threads = threads;
    }

    /**
     * Метод, выполняющий рендеринг части семплов в одном потоке.
     * @param cfg     конфигурация рендера
     * @param samples количество семплов для обработки
     */
    private void renderPart(RenderConfig cfg, int samples) {
        FractalImage canvas = cfg.canvas();
        Rect world = cfg.world();
        List<ColoredTransform> transforms = cfg.transforms();

        int iter = cfg.iterPerSample();
        int symmetry = cfg.symmetry();

        for (int num = 0; num < samples; num++) {
            Point p = RenderUtils.randomPoint(world, ThreadLocalRandom.current());
            for (int i = 0; i < iter; i++) {
                p = RenderUtils.applyTransformAndSymmetry(
                    canvas,
                    world,
                    transforms,
                    symmetry,
                    ThreadLocalRandom.current(),
                    p
                );
            }
        }
    }

    /**
     * Выполняет рендеринг фрактального пламени с использованием нескольких потоков.
     *
     * @param cfg конфигурация рендера
     */
    @Override
    public void render(RenderConfig cfg) {
        int totalSamples = cfg.samplesNum();
        int base = totalSamples / threads;
        int rest = totalSamples % threads;

        ExecutorService executor = Executors.newFixedThreadPool(threads);

        for (int i = 0; i < threads; i++) {
            int taskSamples = (i < rest) ? base + 1 : base;
            executor.submit(() -> renderPart(cfg, taskSamples));
        }

        executor.shutdown();

        try {
            if (!executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS)) {
                System.err.println("Not all tasks finished in awaitTermination.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
