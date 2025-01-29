package backend.academy.service.converter;

import backend.academy.domain.Student;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Использует MethodHandles для вызова name(), surname().
 */
public class MethodHandlesJsonConverter implements JsonMapper {

    private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();
    private static final Map<Class<?>, MethodHandle> NAME_CACHE = new ConcurrentHashMap<>();
    private static final Map<Class<?>, MethodHandle> SURNAME_CACHE = new ConcurrentHashMap<>();

    @Override
    public String convert(Object o) {
        if (!(o instanceof Student)) {
            throw new IllegalArgumentException("Unsupported type: " + o.getClass());
        }
        Class<?> clazz = o.getClass();

        try {
            MethodHandle nameHandle = NAME_CACHE.computeIfAbsent(clazz, key -> {
                try {
                    return LOOKUP.findVirtual(key, "name", MethodType.methodType(String.class));
                } catch (NoSuchMethodException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            });

            MethodHandle surnameHandle = SURNAME_CACHE.computeIfAbsent(clazz, key -> {
                try {
                    return LOOKUP.findVirtual(key, "surname", MethodType.methodType(String.class));
                } catch (NoSuchMethodException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            });

            String name = (String) nameHandle.invoke(o);
            String surname = (String) surnameHandle.invoke(o);

            return "{\"name\":\"" + name + "\",\"surname\":\"" + surname + "\"}";
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
}
