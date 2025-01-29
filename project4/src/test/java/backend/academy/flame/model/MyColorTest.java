package backend.academy.flame.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class MyColorTest {

    @Test
    void shouldMixColors() {
        MyColor c1 = new MyColor(100,200,50);
        MyColor c2 = new MyColor(200,0,100);
        MyColor mixed = c1.mix(c2);
        assertThat(mixed.getR()).isEqualTo((100+200)/2);
        assertThat(mixed.getG()).isEqualTo((200+0)/2);
        assertThat(mixed.getB()).isEqualTo((50+100)/2);
    }
}
