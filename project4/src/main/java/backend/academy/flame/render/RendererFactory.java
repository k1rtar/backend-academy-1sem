package backend.academy.flame.render;

import java.util.Random;

/**
 * Фабрика для создания экземпляров Renderer в зависимости от конфигурации.
 * Если количество потоков равно 1, создается однопоточный рендерер.
 * Если больше 1, создается многопоточный рендерер.
 */
public final class RendererFactory {
    private RendererFactory() {
    }

    /**
     * Создает экземпляр Renderer на основе количества потоков.
     *
     * @param rnd     генератор случайных чисел
     * @param threads количество потоков
     * @return экземпляр Renderer
     */
    public static Renderer createRenderer(Random rnd, int threads) {
        if (threads <= 1) {
            return new SingleThreadedRenderer(rnd);
        } else {
            return new MultiThreadedRenderer(threads);
        }
    }
}
