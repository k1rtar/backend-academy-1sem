package backend.academy.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

@UtilityClass
@Log4j2
public class FileUtils {

    public static Stream<Path> getPaths(List<String> pathPatterns) throws IOException {
        List<Path> matchedPaths = new ArrayList<>();

        for (String pathPattern : pathPatterns) {
            log.info("Обработка шаблона пути: {}", pathPattern);

            // Нормализуем шаблон пути, используя File.separator
            String normalizedPattern = pathPattern.replace("/", File.separator).replace("\\", File.separator);

            if (!normalizedPattern.contains("*") && !normalizedPattern.contains("?")) {
                // Точный путь к файлу
                Path path = Paths.get(normalizedPattern).toAbsolutePath().normalize();
                if (Files.exists(path) && Files.isRegularFile(path)) {
                    log.info("Найден файл: {}", path);
                    matchedPaths.add(path);
                } else {
                    log.warn("Файл не найден: {}", path);
                    throw new IllegalArgumentException("Не найден файл по точному пути: " + pathPattern);
                }
            } else {
                // Шаблон с подстановочными символами
                String basePathString = determineBasePath(normalizedPattern);
                Path basePath = Paths.get(basePathString).toAbsolutePath().normalize();

                // Проверяем существование базового пути
                if (!Files.exists(basePath) || !Files.isDirectory(basePath)) {
                    log.error("Базовый путь не существует или не является директорией: {}", basePath);
                    throw new IOException("Базовый путь не существует или не является директорией: " + basePath);
                }

                // Вычисляем относительный шаблон
                String relativePattern;
                if (basePathString.equals(".") || basePathString.isEmpty()) {
                    // Базовый путь - текущая директория
                    relativePattern = normalizedPattern;
                } else {
                    relativePattern = normalizedPattern.substring(basePathString.length());
                    if (relativePattern.startsWith(File.separator)) {
                        relativePattern = relativePattern.substring(1);
                    }
                }

                log.info("Базовый путь для шаблона '{}': {}", pathPattern, basePath);
                log.info("Относительный шаблон: '{}'", relativePattern);

                final String finalRelativePattern = relativePattern;

                final PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + finalRelativePattern);

                try (Stream<Path> stream = Files.walk(basePath)) {
                    stream.filter(Files::isRegularFile)
                        .peek(path -> log.debug("Обнаружен файл: {}", path))
                        .filter(path -> {
                            Path relPath = basePath.relativize(path);
                            boolean matches = matcher.matches(relPath);
                            if (matches) {
                                log.debug("Файл '{}' соответствует шаблону '{}'", path, finalRelativePattern);
                            }
                            return matches;
                        })
                        .forEach(path -> {
                            log.info("Соответствующий файл: {}", path);
                            matchedPaths.add(path);
                        });
                } catch (IOException e) {
                    log.error("Ошибка при обходе файловой системы от пути: {}", basePath, e);
                    throw new IOException("Ошибка при обходе файловой системы от пути: " + basePath, e);
                }
            }
        }

        if (matchedPaths.isEmpty()) {
            log.warn("Не найдено файлов для шаблонов: {}", pathPatterns);
            throw new IllegalArgumentException("Не найдено файлов по заданным путям: " + pathPatterns);
        }

        return matchedPaths.stream();
    }

    private static String determineBasePath(String pattern) {
        // Нормализуем разделители пути, используя File.separator
        String normalizedPattern = pattern.replace("/", File.separator).replace("\\", File.separator);
        int wildcardIndex = indexOfWildcard(normalizedPattern);
        String basePathString;
        if (wildcardIndex == -1) {
            // Нет символов подстановки, базовый путь — сам шаблон
            basePathString = normalizedPattern;
        } else {
            int lastSeparatorBeforeWildcard = normalizedPattern.lastIndexOf(File.separatorChar, wildcardIndex);
            if (lastSeparatorBeforeWildcard == -1) {
                // Нет разделителя перед символом подстановки, базовый путь — текущая директория
                basePathString = ".";
            } else {
                basePathString = normalizedPattern.substring(0, lastSeparatorBeforeWildcard);
            }
        }
        log.info("Определен базовый путь: {}", basePathString);
        return basePathString;
    }

    private static int indexOfWildcard(String pattern) {
        int idxStar = pattern.indexOf('*');
        int idxQuestion = pattern.indexOf('?');
        if (idxStar == -1) {
            return idxQuestion;
        }
        if (idxQuestion == -1) {
            return idxStar;
        }
        return Math.min(idxStar, idxQuestion);
    }
}
