package backend.academy.flame.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Класс, представляющий пиксель с цветом и количеством попаданий.
 */
@Getter
public class Pixel {
    private MyColor myColor;
    @Setter
    private int hitCount;

    public Pixel(MyColor myColor, int hitCount) {
        this.myColor = myColor;
        this.hitCount = hitCount;
    }

    /**
     * Устанавливает новый цвет пикселя.
     *
     * @param myColor новый цвет
     */
    public void setMyColor(MyColor myColor) {
        this.myColor = myColor;
    }

    /**
     * Смешивает текущий цвет пикселя с новым цветом.
     * Если это первое попадание, устанавливает новый цвет.
     * Иначе усредняет текущий цвет с новым.
     *
     * @param c новый цвет для смешивания
     */
    public void mixColor(MyColor c) {
        if (this.hitCount == 0) {
            this.myColor = c;
        } else {
            this.myColor = this.myColor.mix(c);
        }
    }

    /**
     * Увеличивает счетчик попаданий на единицу.
     */
    public void incHitCount() {
        this.hitCount++;
    }
}
