package backend.academy.flame.transform;

import backend.academy.flame.model.Point;
import java.util.Random;

/**
 * Класс, представляющий аффинную трансформацию.
 * Аффинные трансформации включают линейные преобразования и сдвиги.
 */
public final class Affine implements Transform {

    private static final double BOUND = 1.5;
    private static final double EPS = 1e-6;
    private final Coeff c;

    /**
     * Приватный конструктор для создания аффинной трансформации с заданными коэффициентами.
     *
     * @param c объект Coeff, содержащий коэффициенты трансформации
     */
    private Affine(Coeff c) {
        this.c = c;
    }

    /**
     * Создает новую аффинную трансформацию с случайными коэффициентами.
     *
     * @param rnd генератор случайных чисел
     * @return новый экземпляр Affine
     */
    public static Affine create(Random rnd) {
        return new Affine(getCoefficients(rnd));
    }

    private static boolean check(Coeff coef) {
        double det = coef.a * coef.e - coef.b * coef.d;
        return Math.abs(det) > EPS;
    }

    /**
     * Генерирует случайный коэффициент в диапазоне [-BOUND, BOUND).
     *
     * @param r генератор случайных чисел
     * @return случайный коэффициент
     */
    private static double getC(Random r) {
        return r.nextDouble(-BOUND, BOUND);
    }

    /**
     * Генерирует набор коэффициентов для аффинной трансформации.
     * Повторяет генерацию до тех пор, пока не получит корректные коэффициенты.
     *
     * @param r генератор случайных чисел
     * @return объект Coeff с набором коэффициентов
     */
    private static Coeff getCoefficients(Random r) {
        Coeff coef;
        do {
            coef = new Coeff(getC(r), getC(r), getC(r), getC(r), getC(r), getC(r));
        } while (!check(coef));
        return coef;
    }

    /**
     * Применяет аффинную трансформацию к заданной точке.
     *
     * @param point исходная точка
     * @return трансформированная точка
     */
    @Override
    public Point apply(Point point) {
        return new Point(
            c.a * point.x() + c.b * point.y() + c.c,
            c.d * point.x() + c.e * point.y() + c.f
        );
    }

    private record Coeff(double a, double b, double c, double d, double e, double f) {
    }
}
