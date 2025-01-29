package backend.academy.flame.render;


/**
 * Интерфейс Renderer для рендеринга фрактального пламени.
 * Может быть реализован как однопоточным, так и многопоточным рендерером.
 */
public interface Renderer {
    /**
     * Рендерит фрактал с заданной конфигурацией.
     *
     * @param cfg конфигурация рендера
     */
    void render(RenderConfig cfg);
}
