package backend.academy.flame.main;

import backend.academy.flame.Main;
import backend.academy.flame.config.Config;
import backend.academy.flame.config.ConfigLoader;
import backend.academy.flame.render.RendererFactory;
import backend.academy.flame.render.Renderer;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class MainTest {

    @Test
    void shouldRunWithoutErrors() {
        // GIVEN
        try (MockedStatic<ConfigLoader> loaderMock = Mockito.mockStatic(ConfigLoader.class);
             MockedStatic<RendererFactory> factoryMock = Mockito.mockStatic(RendererFactory.class)) {
            Config cfg = new Config();
            loaderMock.when(() -> ConfigLoader.load(anyString())).thenReturn(cfg);

            Renderer renderer = mock(Renderer.class);
            factoryMock.when(() -> RendererFactory.createRenderer(any(), anyInt())).thenReturn(renderer);

            String[] args = {"--config","some.json"};

            // WHEN
            Main.main(args);

            // THEN
            verify(renderer, atLeastOnce()).render(any());
        }
    }
}
