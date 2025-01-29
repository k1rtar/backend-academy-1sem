package backend.academy.flame.config;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.assertj.core.api.Assertions.*;

class ConfigLoaderTest {

    @Test
    void shouldLoadConfig() throws IOException {
        // GIVEN
        String json = """
        {
          "seed": 123,
          "threads": 2
        }
        """;
        Path temp = Files.createTempFile("config", ".json");
        Files.writeString(temp, json);

        // WHEN
        Config cfg = ConfigLoader.load(temp.toString());

        // THEN
        assertThat(cfg.getSeed()).isEqualTo(123);
        assertThat(cfg.getThreads()).isEqualTo(2);
    }

    @Test
    void shouldFailIfNoFile() {
        // GIVEN
        String invalidPath = "no_such_file.json";

        // WHEN & THEN
        assertThatThrownBy(() -> ConfigLoader.load(invalidPath))
            .isInstanceOf(IOException.class);
    }
}
