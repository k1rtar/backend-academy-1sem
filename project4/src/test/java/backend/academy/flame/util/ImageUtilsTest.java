package backend.academy.flame.util;

import backend.academy.flame.model.*;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.assertj.core.api.Assertions.assertThat;

class ImageUtilsTest {

    @Test
    void shouldDecodeHex() {
        MyColor c = ImageUtils.decodeHex("#ff00ff");
        assertThat(c.getR()).isEqualTo(255);
        assertThat(c.getG()).isEqualTo(0);
        assertThat(c.getB()).isEqualTo(255);
    }

    @Test
    void shouldSaveImage() throws IOException {
        FractalImage img = FractalImage.create(2,2,()->new Pixel(new MyColor(255,0,0),0));
        Path temp = Files.createTempFile("testImage",".png");
        ImageUtils.save(img, temp, "png");
        assertThat(Files.size(temp)).isGreaterThan(0);
    }
}
