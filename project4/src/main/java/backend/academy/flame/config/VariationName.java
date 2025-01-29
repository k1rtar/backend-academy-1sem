package backend.academy.flame.config;

/**
 * Класс, представляющий трансформацию и её вес.
 * Используется для задания набора трансформационных функций в конфигурации.
 *
 * @param name   имя трансформации
 * @param weight вес трансформации при выборе случайной функции
 */
public record VariationName(String name, double weight) {}
