package backend.academy.flame.config;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ConfigTest {

    @Test
    void shouldUseDefaultValues() {
        // GIVEN
        Config c = new Config();

        // WHEN & THEN
        assertThat(c.getSeed()).isEqualTo(13377331); // Допустим это дефолт
        assertThat(c.getThreads()).isEqualTo(1);
        // и т.д. проверить все поля.
    }

    @Test
    void shouldSetAndGetValues() {
        // GIVEN
        Config c = new Config();
        c.setSeed(999);
        c.setThreads(4);

        // WHEN & THEN
        assertThat(c.getSeed()).isEqualTo(999);
        assertThat(c.getThreads()).isEqualTo(4);
    }
}
