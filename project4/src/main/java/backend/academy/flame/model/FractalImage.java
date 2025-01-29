package backend.academy.flame.model;

import java.util.Arrays;
import java.util.function.Supplier;

/**
 * Класс, представляющий фрактальное изображение.
 * Хранит массив пикселей, а также размеры изображения.
 */
public record FractalImage(Pixel[] data, int width, int height) {
    /**
     * Создает новое фрактальное изображение с заданными размерами и конструктором пикселей.
     *
     * @param width     ширина изображения
     * @param height    высота изображения
     * @param pixelCtor конструктор для создания новых пикселей
     * @return новый экземпляр FractalImage
     */
    public static FractalImage create(int width, int height, Supplier<? extends Pixel> pixelCtor) {
        Pixel[] pixels = new Pixel[width * height];
        Arrays.setAll(pixels, i -> pixelCtor.get());
        return new FractalImage(pixels, width, height);
    }

    /**
     * Проверяет, находится ли точка с координатами (x, y) внутри изображения.
     *
     * @param x координата по оси X
     * @param y координата по оси Y
     * @return true, если точка внутри изображения, иначе false
     */
    public boolean contains(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    /**
     * Возвращает пиксель по координатам (x, y).
     *
     * @param x координата по оси X
     * @param y координата по оси Y
     * @return объект Pixel на заданных координатах
     */
    public Pixel pixel(int x, int y) {
        return data[y * width + x];
    }
}
