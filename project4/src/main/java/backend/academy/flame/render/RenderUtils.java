package backend.academy.flame.render;

import backend.academy.flame.model.FractalImage;
import backend.academy.flame.model.Pixel;
import backend.academy.flame.model.Point;
import backend.academy.flame.model.Rect;
import backend.academy.flame.transform.ColoredTransform;
import java.util.List;
import java.util.Random;

/**
 * Вспомогательный класс с утилитными методами для рендеринга.
 */
public final class RenderUtils {

    private RenderUtils() {
    }

    /**
     * Генерирует случайную точку внутри заданного прямоугольника.
     *
     * @param w   прямоугольная область
     * @param rnd генератор случайных чисел
     * @return случайная точка внутри области
     */
    public static Point randomPoint(Rect w, Random rnd) {
        double x = rnd.nextDouble(w.x(), w.xRight());
        double y = rnd.nextDouble(w.y(), w.yTop());
        return new Point(x, y);
    }

    /**
     * Поворачивает точку вокруг центра прямоугольника на заданный угол.
     *
     * @param p     исходная точка
     * @param w     прямоугольная область
     * @param theta угол поворота в радианах
     * @return повернутая точка
     */
    public static Point rotateAroundCenter(Point p, Rect w, double theta) {
        Point c = new Point(w.x() + w.width() / 2, w.y() + w.height() / 2);
        double dx = p.x() - c.x();
        double dy = p.y() - c.y();
        return new Point(
            dx * Math.cos(theta) - dy * Math.sin(theta) + c.x(),
            dx * Math.sin(theta) + dy * Math.cos(theta) + c.y()
        );
    }

    /**
     * Преобразует координаты точки из мира в координаты канвы.
     *
     * @param p   точка в мире
     * @param w   прямоугольная область мира
     * @param img канва для отображения
     * @return точка в координатах канвы
     */
    public static Point mapToCanvas(Point p, Rect w, FractalImage img) {
        double nx = (p.x() - w.x()) / w.width() * img.width();
        double ny = (p.y() - w.y()) / w.height() * img.height();
        return new Point(nx, ny);
    }

    /**
     * Применяет трансформацию и симметрию к точке, обновляет канву.
     *
     * @param canvas    канва для обновления пикселей
     * @param world     прямоугольная область мира
     * @param transforms список трансформаций
     * @param symmetry  параметр симметрии
     * @param rnd       генератор случайных чисел
     * @param p         исходная точка
     * @return трансформированная точка
     */
    public static Point applyTransformAndSymmetry(
        FractalImage canvas,
        Rect world,
        List<ColoredTransform> transforms,
        int symmetry,
        Random rnd,
        Point p
    ) {
        ColoredTransform ct = transforms.get(rnd.nextInt(transforms.size()));
        Point transformedP = ct.apply(p);

        double angle = 0;
        double step = (Math.PI * 2) / symmetry;
        for (int s = 0; s < symmetry; s++, angle += step) {
            Point rotated = rotateAroundCenter(transformedP, world, angle);
            if (world.contains(rotated)) {
                Point imgPt = mapToCanvas(rotated, world, canvas);
                int x = (int) imgPt.x();
                int y = (int) imgPt.y();
                if (canvas.contains(x, y)) {
                    Pixel px = canvas.pixel(x, y);
                    synchronized (px) {
                        px.mixColor(ct.myColor());
                        px.incHitCount();
                    }
                }
            }
        }
        return transformedP;
    }
}
