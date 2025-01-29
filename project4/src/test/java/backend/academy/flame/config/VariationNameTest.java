package backend.academy.flame.config;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class VariationNameTest {

    @Test
    void recordShouldStoreValues() {
        VariationName vn = new VariationName("Linear",0.5);
        assertThat(vn.name()).isEqualTo("Linear");
        assertThat(vn.weight()).isEqualTo(0.5);
    }
}
