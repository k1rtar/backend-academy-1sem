package backend.academy.flame.model;

import lombok.Getter;

/**
 * Класс, представляющий цвет с каналами r, g, b.
 * Используется для внутреннего хранения цвета пикселя.
 */
@Getter
public class MyColor {
    private final int r;
    private final int g;
    private final int b;

    /**
     * Конструктор для инициализации цвета.
     *
     * @param r значение красного канала (0-255)
     * @param g значение зеленого канала (0-255)
     * @param b значение синего канала (0-255)
     */
    public MyColor(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    /**
     * Смешивает текущий цвет с другим цветом, усредняя их каналы.
     *
     * @param other другой цвет для смешивания
     * @return новый объект MyColor, представляющий смешанный цвет
     */
    public MyColor mix(MyColor other) {
        int nr = (r + other.r) / 2;
        int ng = (g + other.g) / 2;
        int nb = (b + other.b) / 2;
        return new MyColor(nr, ng, nb);
    }
}
