package backend.academy.service.converter;

/**
 * Общий интерфейс для "конвертера" объекта в JSON.
 * Вызываем Student#name() и Student#surname().
 */
public interface JsonMapper {
    String convert(Object o);
}
