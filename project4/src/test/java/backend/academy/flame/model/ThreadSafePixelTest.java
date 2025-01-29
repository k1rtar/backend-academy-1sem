package backend.academy.flame.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ThreadSafePixelTest {

    @Test
    void shouldInitializeThreadSafePixel() {
        // GIVEN
        MyColor color = new MyColor(50, 100, 150);
        ThreadSafePixel pixel = new ThreadSafePixel(color, 0);

        // THEN
        assertThat(pixel.getMyColor()).isEqualTo(color);
        assertThat(pixel.getHitCount()).isEqualTo(0);
    }

    @Test
    void shouldMixColorThreadSafely() {
        // GIVEN
        ThreadSafePixel pixel = new ThreadSafePixel(new MyColor(0, 0, 0), 0);
        MyColor newColor = new MyColor(255, 255, 255);

        // WHEN
        pixel.mixColor(newColor);

        // THEN
        assertThat(pixel.getMyColor()).isEqualTo(newColor);
    }

    @Test
    void shouldIncrementHitCountThreadSafely() {
        // GIVEN
        ThreadSafePixel pixel = new ThreadSafePixel(new MyColor(0, 0, 0), 0);

        // WHEN
        pixel.incHitCount();

        // THEN
        assertThat(pixel.getHitCount()).isEqualTo(1);
    }
}
