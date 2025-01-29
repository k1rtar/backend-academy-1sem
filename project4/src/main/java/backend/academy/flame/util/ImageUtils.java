package backend.academy.flame.util;

import backend.academy.flame.model.FractalImage;
import backend.academy.flame.model.MyColor;
import backend.academy.flame.model.Pixel;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.imageio.ImageIO;

/**
 * Вспомогательный класс с утилитными методами для работы с изображениями.
 */
public final class ImageUtils {
    private ImageUtils() {
    }

    /**
     * Декодирует строку HEX в объект MyColor.
     *
     * @param hex строка в формате #RRGGBB
     * @return объект MyColor с соответствующими значениями каналов
     */
    public static MyColor decodeHex(String hex) {
        Color awtColor = Color.decode(hex);
        return new MyColor(awtColor.getRed(), awtColor.getGreen(), awtColor.getBlue());
    }

    /**
     * Сохраняет фрактальное изображение в файл заданного формата.
     *
     * @param image    фрактальное изображение
     * @param filename путь к файлу для сохранения
     * @param format   формат изображения ("png", "jpg" и т.д.)
     * @throws IOException если возникает ошибка при записи файла
     */
    public static void save(FractalImage image, Path filename, String format) throws IOException {
        BufferedImage bi = toImage(image);
        try (OutputStream out = Files.newOutputStream(filename)) {
            ImageIO.write(bi, format, out);
        }
    }

    /**
     * Преобразует FractalImage в BufferedImage для сохранения.
     *
     * @param image фрактальное изображение
     * @return BufferedImage, готовое для сохранения
     */
    private static BufferedImage toImage(FractalImage image) {
        BufferedImage res = new BufferedImage(image.width(), image.height(), BufferedImage.TYPE_INT_RGB);
        int[] rgbArray = new int[image.width() * image.height()];
        Pixel[] data = image.data();
        for (int i = 0; i < data.length; i++) {
            Pixel p = data[i];
            MyColor c = p.getMyColor();
            Color awtColor = new Color(c.getR(), c.getG(), c.getB());
            rgbArray[i] = awtColor.getRGB();
        }
        res.setRGB(0, 0, image.width(), image.height(), rgbArray, 0, image.width());
        return res;
    }
}
