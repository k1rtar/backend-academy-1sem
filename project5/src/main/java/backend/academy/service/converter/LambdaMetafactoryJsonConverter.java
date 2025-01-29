package backend.academy.service.converter;

import backend.academy.domain.Student;
import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * Использует LambdaMetafactory для создания лямбда-функций:
 * (Object) -> (String) вызывающих Student#name() / #surname().
 */
public class LambdaMetafactoryJsonConverter implements JsonMapper {

    private static final Map<Class<?>, Function<Object, String>> NAME_LAMBDA_CACHE = new ConcurrentHashMap<>();
    private static final Map<Class<?>, Function<Object, String>> SURNAME_LAMBDA_CACHE = new ConcurrentHashMap<>();

    @Override
    public String convert(Object o) {
        if (!(o instanceof Student)) {
            throw new IllegalArgumentException("Unsupported type: " + o.getClass());
        }
        Class<?> clazz = o.getClass();

        Function<Object, String> nameFunc = NAME_LAMBDA_CACHE.computeIfAbsent(clazz, c -> createLambda(c, "name"));
        Function<Object, String> surnameFunc =
            SURNAME_LAMBDA_CACHE.computeIfAbsent(clazz, c -> createLambda(c, "surname"));

        String name = nameFunc.apply(o);
        String surname = surnameFunc.apply(o);

        return "{\"name\":\"" + name + "\",\"surname\":\"" + surname + "\"}";
    }

    @SuppressWarnings("unchecked")
    private Function<Object, String> createLambda(Class<?> clazz, String methodName) {
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            MethodHandle target = lookup.findVirtual(clazz, methodName, MethodType.methodType(String.class));

            // invokedType: (Object) -> Function
            MethodType invokedType = MethodType.methodType(Function.class);
            // samMethodType: (Object, Object) -> Object
            MethodType samMethodType = MethodType.methodType(Object.class, Object.class);
            // instantiation: (clazz) -> String
            MethodType instantiation = MethodType.methodType(String.class, clazz);

            CallSite callSite = LambdaMetafactory.metafactory(
                lookup,
                "apply",
                invokedType,
                samMethodType,
                target,
                instantiation
            );

            return (Function<Object, String>) callSite.getTarget().invoke();
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
}
