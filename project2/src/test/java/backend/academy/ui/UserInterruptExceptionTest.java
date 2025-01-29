package backend.academy.ui;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class UserInterruptExceptionTest {

    @Test
    void shouldStoreMessage() {
        String msg = "User interrupted";
        UserInterruptException ex = new UserInterruptException(msg);
        assertThat(ex.getMessage()).isEqualTo(msg);
    }

    @Test
    void shouldBeRuntimeException() {
        UserInterruptException ex = new UserInterruptException("test");
        assertThat(ex).isInstanceOf(RuntimeException.class);
    }
}
