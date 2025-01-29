package backend.academy.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Messages {
    public static final String ERROR_UNSUPPORTED_CHARSET =
        "Ошибка: Указана неподдерживаемая кодировка. Пожалуйста, проверьте параметр --encoding.";
    public static final String ERROR_ARGUMENTS = "Ошибка в аргументах программы: %s";
    public static final String ERROR_MALFORMED_INPUT =
        "Ошибка: Неверная кодировка файла. Пожалуйста, укажите правильную кодировку с помощью параметра --encoding.";
    public static final String ERROR_FILE_NOT_FOUND =
        "Ошибка: Файл не найден. Проверьте правильность указанного пути.";
    public static final String ERROR_IO_EXCEPTION = "Ошибка ввода-вывода: %s";
    public static final String ERROR_UNEXPECTED = "Непредвиденная ошибка: %s";
}
