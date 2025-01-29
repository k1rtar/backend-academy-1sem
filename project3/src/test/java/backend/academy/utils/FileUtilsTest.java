package backend.academy.utils;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class FileUtilsTest {

    @TempDir
    Path tempDir;

    @Test
    public void testGetPaths_WithExactFilePath() throws IOException {
        // Arrange
        Path logFile = tempDir.resolve("test.log");
        Files.createFile(logFile);
        List<String> paths = List.of(logFile.toString());

        // Act
        Stream<Path> result = FileUtils.getPaths(paths);

        // Assert
        List<Path> pathList = result.toList();
        assertThat(pathList).hasSize(1);
        assertThat(pathList.get(0)).isEqualTo(logFile);
    }

    @Test
    public void testGetPaths_WithGlobPattern() throws IOException {
        // Arrange
        Path accessLog = tempDir.resolve("access.log");
        Path errorLog = tempDir.resolve("error.log");
        Files.createFile(accessLog);
        Files.createFile(errorLog);
        List<String> paths = List.of(tempDir.toString() + "/*.log");

        // Act
        Stream<Path> result = FileUtils.getPaths(paths);

        // Assert
        List<Path> pathList = result.toList();
        assertThat(pathList).hasSize(2);
        assertThat(pathList).containsExactlyInAnyOrder(accessLog, errorLog);
    }

    @Test
    public void testGetPaths_InvalidPath_ShouldThrowException() {
        // Arrange
        Path invalidPath = tempDir.resolve("invalid");
        List<String> paths = List.of(invalidPath.toString());

        // Act
        Throwable thrown = catchThrowable(() -> FileUtils.getPaths(paths).toList());

        // Assert
        assertThat(thrown)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Не найден файл по точному пути: " + invalidPath.toString());
    }
}
