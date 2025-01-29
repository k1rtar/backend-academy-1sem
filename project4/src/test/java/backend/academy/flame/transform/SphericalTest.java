package backend.academy.flame.transform;

import backend.academy.flame.model.Point;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.within;

class SphericalTest {

    @ParameterizedTest
    @CsvSource({
        "1,1",
        "0,0",
        "10,0",
        "-5,5"
    })
    void shouldApplySphericalTransform(double x, double y) {
        // GIVEN
        Spherical spherical = new Spherical();
        Point p = new Point(x,y);

        // WHEN
        Point result = spherical.apply(p);

        // THEN
        double r = Math.sqrt(x*x + y*y);
        double expectedX = (x)/(r*r);
        double expectedY = (y)/(r*r);
        if (r == 0) {
            // r=0 => division by zero, обычно spherical вернёт NaN. Проверим, что логика корректна.
            // Допустим, для r=0 мы ожидаем (0,0) или некий graceful fallback.
            assertThat(result.x()).isNaN();
            assertThat(result.y()).isNaN();
        } else {
            assertThat(result.x()).isCloseTo(expectedX, within(1e-9));
            assertThat(result.y()).isCloseTo(expectedY, within(1e-9));
        }
    }
}
