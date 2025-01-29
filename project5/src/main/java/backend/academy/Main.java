package backend.academy;

import backend.academy.domain.Student;
import backend.academy.service.converter.DirectJsonConverter;
import backend.academy.service.converter.JsonMapper;
import backend.academy.service.converter.LambdaMetafactoryJsonConverter;
import backend.academy.service.converter.MethodHandlesJsonConverter;
import backend.academy.service.converter.ReflectionJsonConverter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import lombok.experimental.UtilityClass;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

/**
 * Запускает JMH-бенчмарки, читая настройки из application.properties
 * (время прогрева, количество итераций, и т.д.).
 */
@UtilityClass
public class Main {

    public static void main(String[] args) throws RunnerException {

        Properties props = new Properties();
        try (InputStream is = Main.class
            .getClassLoader()
            .getResourceAsStream("application.properties")) {
            if (is != null) {
                props.load(is);
            } else {
                throw new IllegalStateException("application.properties not found on classpath!");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties", e);
        }

        int forks = Integer.parseInt(props.getProperty("jmh.forks", "1"));
        int warmupForks = Integer.parseInt(props.getProperty("jmh.warmupForks", "1"));
        int warmupIterations = Integer.parseInt(props.getProperty("jmh.warmupIterations", "1"));
        int warmupSeconds = Integer.parseInt(props.getProperty("jmh.warmupSeconds", "5"));
        int measurementIterations = Integer.parseInt(props.getProperty("jmh.measurementIterations", "1"));
        int measurementSeconds = Integer.parseInt(props.getProperty("jmh.measurementSeconds", "5"));

        Options options = new OptionsBuilder()
            .include(BenchTests.class.getSimpleName())
            .shouldFailOnError(true)
            .shouldDoGC(true)
            .mode(Mode.AverageTime)
            .timeUnit(TimeUnit.NANOSECONDS)

            // Количество форков JVM
            .forks(forks)
            // Количество прогревочных форков
            .warmupForks(warmupForks)

            // Прогрев: warmupIterations итераций по warmupSeconds секунд
            .warmupIterations(warmupIterations)
            .warmupTime(TimeValue.seconds(warmupSeconds))

            // Основные измерения: measurementIterations итераций по measurementSeconds секунд
            .measurementIterations(measurementIterations)
            .measurementTime(TimeValue.seconds(measurementSeconds))

            .build();

        new Runner(options).run();
    }

    /**
     * Внутренний класс JMH с 4 бенчмарк-методами:
     * Прямой доступ, Рефлексия, MethodHandles, LambdaMetafactory.
     */
    @State(Scope.Thread)
    public static class BenchTests {

        private Student student;

        private JsonMapper direct;
        private JsonMapper reflection;
        private JsonMapper methodHandles;
        private JsonMapper lambdaMetafactory;

        @Setup
        public void setup() {
            student = new Student("Alexander", "Biryukov");

            direct = new DirectJsonConverter();
            reflection = new ReflectionJsonConverter();
            methodHandles = new MethodHandlesJsonConverter();
            lambdaMetafactory = new LambdaMetafactoryJsonConverter();
        }

        @Benchmark
        public void directAccess(Blackhole bh) {
            String json = direct.convert(student);
            bh.consume(json);
        }

        @Benchmark
        public void reflection(Blackhole bh) throws InvocationTargetException, IllegalAccessException {
            String json = reflection.convert(student);
            bh.consume(json);
        }

        @Benchmark
        public void methodHandles(Blackhole bh) {
            String json = methodHandles.convert(student);
            bh.consume(json);
        }

        @Benchmark
        public void lambdaMetafactory(Blackhole bh) {
            String json = lambdaMetafactory.convert(student);
            bh.consume(json);
        }
    }
}
