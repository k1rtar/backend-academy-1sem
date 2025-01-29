package backend.academy.flame.post;

import backend.academy.flame.model.FractalImage;

/**
 * Интерфейс для пост-обработки фрактального изображения.
 * Реализует функциональный интерфейс для применения обработки к изображению.
 */
@FunctionalInterface
public interface ImageProcessor {
    /**
     * Применяет пост-обработку к заданному фрактальному изображению.
     *
     * @param image фрактальное изображение для обработки
     */
    void process(FractalImage image);
}
