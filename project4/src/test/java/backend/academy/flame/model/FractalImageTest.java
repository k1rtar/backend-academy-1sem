package backend.academy.flame.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class FractalImageTest {

    @Test
    void shouldCreateImage() {
        FractalImage img = FractalImage.create(10,10,()-> new Pixel(new MyColor(0,0,0),0));
        assertThat(img.width()).isEqualTo(10);
        assertThat(img.height()).isEqualTo(10);
        assertThat(img.data().length).isEqualTo(100);
    }

    @Test
    void shouldContainCoordinates() {
        FractalImage img = FractalImage.create(5,5,()-> new Pixel(new MyColor(0,0,0),0));
        assertThat(img.contains(4,4)).isTrue();
        assertThat(img.contains(-1,0)).isFalse();
    }
}
