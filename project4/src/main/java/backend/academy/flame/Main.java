package backend.academy.flame;

import backend.academy.flame.config.Config;
import backend.academy.flame.config.ConfigLoader;
import backend.academy.flame.model.FractalImage;
import backend.academy.flame.model.MyColor;
import backend.academy.flame.model.Rect;
import backend.academy.flame.post.ImageProcessor;
import backend.academy.flame.post.LogarithmicGammaCorrection;
import backend.academy.flame.render.RenderConfig;
import backend.academy.flame.render.Renderer;
import backend.academy.flame.render.RendererFactory;
import backend.academy.flame.transform.Affine;
import backend.academy.flame.transform.ColoredTransform;
import backend.academy.flame.transform.Horseshoe;
import backend.academy.flame.transform.Linear;
import backend.academy.flame.transform.Sinusoidal;
import backend.academy.flame.transform.Spherical;
import backend.academy.flame.transform.SumTransform;
import backend.academy.flame.transform.Swirl;
import backend.academy.flame.transform.Transform;
import backend.academy.flame.transform.WeightedTransform;
import backend.academy.flame.util.ImageUtils;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.common.io.Files;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import lombok.extern.log4j.Log4j2;

/**
 * Главный класс приложения для генерации фрактального пламени.
 * Загружает конфигурацию, выполняет рендеринг и сохраняет изображение.
 */
@Log4j2
public class Main {

    private static final Map<String, Transform> KNOWN_TRANSFORMS = Map.of(
        "Linear", new Linear(),
        "Sinusoidal", new Sinusoidal(),
        "Spherical", new Spherical(),
        "Swirl", new Swirl(),
        "Horseshoe", new Horseshoe()
    );

    @Parameter(
        names = "--config",
        description = "Path to the JSON config file",
        required = true
    )
    private String configPath;

    public static void main(String[] args) {
        Main main = new Main();
        JCommander.newBuilder()
            .addObject(main)
            .build()
            .parse(args);
        main.run();
    }

    /**
     * Метод, выполняющий основную логику приложения:
     * - загрузка конфигурации,
     * - проверка корректности,
     * - рендеринг,
     * - пост-обработка,
     * - сохранение изображения.
     */
    private void run() {
        boolean shouldStop = false;
        Config config = null;
        try {
            config = ConfigLoader.load(configPath);
        } catch (IOException e) {
            log.error("Failed to load config: {}", e.getMessage());
            shouldStop = true;
        }

        if (!shouldStop) {
            if (config == null) {
                log.error("Config is null, cannot proceed.");
                shouldStop = true;
            }
        }

        if (!shouldStop) {
            boolean hasUnknown = config.getVariations().stream()
                .anyMatch(v -> !KNOWN_TRANSFORMS.containsKey(v.name()));
            if (hasUnknown) {
                log.error("Config contains unknown variation name, stopping.");
                shouldStop = true;
            }
        }

        if (!shouldStop) {
            List<Transform> variations = config.getVariations().stream()
                .map(v -> new WeightedTransform(
                    KNOWN_TRANSFORMS.get(v.name()),
                    v.weight()
                ))
                .map(Transform.class::cast)
                .toList();

            List<MyColor> myColors = config.getColors().stream()
                .map(ImageUtils::decodeHex)
                .toList();

            Random rnd = new Random(config.getSeed());

            List<ColoredTransform> transforms = new ArrayList<>();
            for (MyColor c : myColors) {
                Transform sumT = new SumTransform(Affine.create(rnd), variations);
                transforms.add(new ColoredTransform(sumT, c));
            }

            Renderer renderer = RendererFactory.createRenderer(rnd, config.getThreads());
            FractalImage canvas = FractalImage.create(
                config.getWidth(),
                config.getHeight(),
                config.getThreads() == 1
                    ? () -> new backend.academy.flame.model.Pixel(new MyColor(0, 0, 0), 0)
                    : () -> new backend.academy.flame.model.ThreadSafePixel(new MyColor(0, 0, 0), 0)
            );

            double xB = config.getXBound();
            double yB = config.getYBound();
            Rect world = new Rect(-xB, -yB, xB * 2.0, yB * 2.0);

            RenderConfig renderConfig = new RenderConfig(
                canvas,
                world,
                transforms,
                config.getSamplesNum(),
                config.getIterPerSample(),
                config.getSymmetry()
            );

            long startNs = System.nanoTime();
            renderer.render(renderConfig);
            long endNs = System.nanoTime();
            long elapsedMs = TimeUnit.NANOSECONDS.toMillis(endNs - startNs);
            log.info("Rendering completed in {} ms", elapsedMs);


            ImageProcessor gammaProc = new LogarithmicGammaCorrection(config.getGamma());
            gammaProc.process(canvas);


            try {
                Path p = Path.of(config.getOutput());
                String ext = Files.getFileExtension(config.getOutput());
                if (ext.isEmpty()) {
                    ext = "png";
                }
                ImageUtils.save(canvas, p, ext);
                log.info("Image saved to {}", p);
            } catch (InvalidPathException | IOException e) {
                log.error("Failed to save image: {}", e.getMessage());
            }
        }
    }
}
