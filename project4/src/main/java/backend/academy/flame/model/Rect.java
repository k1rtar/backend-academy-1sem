package backend.academy.flame.model;

/**
 * Класс, представляющий прямоугольную область ("мир") для генерации фрактала.
 *
 * @param x      координата нижнего левого угла по оси X
 * @param y      координата нижнего левого угла по оси Y
 * @param width  ширина области
 * @param height высота области
 */
public record Rect(double x, double y, double width, double height) {
    /**
     * Проверяет, содержится ли заданная точка внутри области.
     *
     * @param p точка для проверки
     * @return true, если точка внутри области, иначе false
     */
    public boolean contains(Point p) {
        return p.x() >= x && p.x() <= xRight() && p.y() >= y && p.y() <= yTop();
    }

    /**
     * Возвращает правую границу области по оси X.
     *
     * @return координата правой границы
     */
    public double xRight() {
        return x + width;
    }

    /**
     * Возвращает верхнюю границу области по оси Y.
     *
     * @return координата верхней границы
     */
    public double yTop() {
        return y + height;
    }
}
