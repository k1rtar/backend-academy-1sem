package backend.academy.flame.model;

/**
 * Класс, представляющий потокобезопасный пиксель.
 * Расширяет класс Pixel и синхронизирует методы для безопасного доступа из нескольких потоков.
 */
public class ThreadSafePixel extends Pixel {

    public ThreadSafePixel(MyColor myColor, int hitCount) {
        super(myColor, hitCount);
    }

    /**
     * Синхронизированный метод для смешивания цвета.
     *
     * @param c новый цвет для смешивания
     */
    @Override
    public synchronized void mixColor(MyColor c) {
        super.mixColor(c);
    }

    /**
     * Синхронизированный метод для увеличения счетчика попаданий.
     */
    @Override
    public synchronized void incHitCount() {
        super.incHitCount();
    }

    /**
     * Синхронизированный метод для установки нового цвета пикселя.
     *
     * @param myColor новый цвет
     */
    public synchronized void setMyColor(MyColor myColor) {
        super.setMyColor(myColor);
    }
}
