package backend.academy.flame.render;

import backend.academy.flame.model.FractalImage;
import backend.academy.flame.model.Rect;
import backend.academy.flame.transform.ColoredTransform;
import java.util.List;
import lombok.With;

/**
 * Класс, представляющий конфигурацию для рендера фрактального изображения.
 * Используется для передачи всех необходимых параметров в рендерер.
 */
@With
public record RenderConfig(
    FractalImage canvas,
    Rect world,
    List<ColoredTransform> transforms,
    int samplesNum,
    int iterPerSample,
    int symmetry
) {
}
