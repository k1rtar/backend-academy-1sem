package backend.academy.flame.render;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ImageFormatTest {

    @Test
    void shouldHaveAllFormats() {
        // GIVEN & WHEN
        ImageFormat[] formats = ImageFormat.values();

        // THEN
        assertThat(formats).containsExactly(ImageFormat.JPEG, ImageFormat.BMP, ImageFormat.PNG);
    }

    @Test
    void shouldReturnCorrectFormatName() {
        // GIVEN
        ImageFormat jpeg = ImageFormat.JPEG;
        ImageFormat bmp = ImageFormat.BMP;
        ImageFormat png = ImageFormat.PNG;

        // THEN
        assertThat(jpeg.name()).isEqualTo("JPEG");
        assertThat(bmp.name()).isEqualTo("BMP");
        assertThat(png.name()).isEqualTo("PNG");
    }
}
