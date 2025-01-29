package backend.academy.reader;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import backend.academy.utils.FileUtils;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

public class FileLogReaderTest {

    @Test
    public void testReadLines_FileReadException_ShouldThrowRuntimeException() {
        // Arrange
        List<String> paths = List.of("invalid/path/*.log");
        FileLogReader reader = new FileLogReader(paths, StandardCharsets.UTF_8);

        try (MockedStatic<FileUtils> mockedFileUtils = mockStatic(FileUtils.class)) {
            // Мокаем FileUtils.getPaths(...) чтобы выбросить IOException для любых списков путей
            mockedFileUtils.when(() -> FileUtils.getPaths(anyList()))
                .thenThrow(new IOException("Базовый путь не существует или не является директорией: invalid/path"));

            // Act & Assert
            Throwable thrown = catchThrowable(() -> reader.readLines().toList());

            // Assert
            assertThat(thrown)
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Ошибка при чтении файлов");
        }
    }
}
