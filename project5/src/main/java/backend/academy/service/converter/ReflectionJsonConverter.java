package backend.academy.service.converter;

import backend.academy.domain.Student;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Используем java.lang.reflect.Method.
 * Кэшируем Method в статических ConcurrentHashMap, чтобы ускорить доступ.
 */
public class ReflectionJsonConverter implements JsonMapper {

    private static final Map<Class<?>, Method> NAME_CACHE = new ConcurrentHashMap<>();
    private static final Map<Class<?>, Method> SURNAME_CACHE = new ConcurrentHashMap<>();

    @Override
    public String convert(Object o) {
        if (!(o instanceof Student)) {
            throw new IllegalArgumentException("Unsupported type: " + o.getClass());
        }
        Class<?> clazz = o.getClass();

        try {
            Method nameMethod = NAME_CACHE.computeIfAbsent(clazz, key -> {
                try {
                    Method m = key.getMethod("name");
                    m.setAccessible(true);
                    return m;
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            });

            Method surnameMethod = SURNAME_CACHE.computeIfAbsent(clazz, key -> {
                try {
                    Method m = key.getMethod("surname");
                    m.setAccessible(true);
                    return m;
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            });

            String name = (String) nameMethod.invoke(o);
            String surname = (String) surnameMethod.invoke(o);

            return "{\"name\":\"" + name + "\",\"surname\":\"" + surname + "\"}";
        } catch (Exception e) {
            throw new RuntimeException("Reflection call failed", e);
        }
    }
}
