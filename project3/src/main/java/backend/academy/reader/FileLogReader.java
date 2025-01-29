package backend.academy.reader;

import backend.academy.utils.FileUtils;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class FileLogReader implements LogReader {
    private final List<String> paths;
    private final Charset charset;

    public FileLogReader(List<String> paths, Charset charset) {
        this.paths = paths;
        this.charset = charset != null ? charset : StandardCharsets.UTF_8;
    }

    @Override
    public Stream<String> readLines() {
        try {
            return FileUtils.getPaths(paths)
                .flatMap(this::linesFromFile);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении файлов: " + e.getMessage(), e);
        }
    }

    private Stream<String> linesFromFile(Path file) {
        try {
            return Files.lines(file, charset);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении файла: " + file.toString(), e);
        }
    }

    @Override
    public void close() throws Exception {
    }
}
