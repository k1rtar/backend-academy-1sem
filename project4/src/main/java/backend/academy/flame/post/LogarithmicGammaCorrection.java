package backend.academy.flame.post;

import backend.academy.flame.model.FractalImage;
import backend.academy.flame.model.MyColor;
import backend.academy.flame.model.Pixel;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Класс для применения логарифмической гамма-коррекции к фрактальному изображению.
 */
public class LogarithmicGammaCorrection implements ImageProcessor {

    private static final int MAX_COLOR_VALUE = 255;
    private final double gamma;

    public LogarithmicGammaCorrection(double gamma) {
        this.gamma = gamma;
    }

    /**
     * Применяет логарифмическую гамма-коррекцию к изображению.
     *
     * @param image фрактальное изображение для обработки
     */
    @Override
    public void process(FractalImage image) {
        int maxHit = Arrays.stream(image.data())
            .max(Comparator.comparingInt(Pixel::getHitCount))
            .orElseThrow()
            .getHitCount();

        if (maxHit == 0) {
            return;
        }

        double logMax = Math.log10(maxHit);

        for (int y = 0; y < image.height(); y++) {
            for (int x = 0; x < image.width(); x++) {
                Pixel p = image.pixel(x, y);
                int hits = p.getHitCount();
                if (hits == 0) {
                    continue;
                }
                p.setMyColor(new MyColor(
                    correctColor(p.getMyColor().getR(), hits, logMax),
                    correctColor(p.getMyColor().getG(), hits, logMax),
                    correctColor(p.getMyColor().getB(), hits, logMax)
                ));
            }
        }
    }

    /**
     * Корректирует значение цвета на основе гамма-коррекции.
     *
     * @param c      исходное значение цвета
     * @param hits   количество попаданий
     * @param logMax логарифм максимального количества попаданий
     * @return скорректированное значение цвета
     */
    private int correctColor(int c, int hits, double logMax) {
        double factor = Math.pow(Math.log10(hits) / logMax, 1 / gamma);
        int val = (int) Math.round(c * factor);
        return Math.max(0, Math.min(MAX_COLOR_VALUE, val));
    }
}
