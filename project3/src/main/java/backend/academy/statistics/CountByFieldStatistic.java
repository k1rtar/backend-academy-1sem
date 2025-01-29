package backend.academy.statistics;

import backend.academy.model.LogRecord;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CountByFieldStatistic<T> implements Statistic<Map<T, Long>> {
    private final Map<T, Long> counts = new HashMap<>();
    private final Function<LogRecord, T> classifier;

    public CountByFieldStatistic(Function<LogRecord, T> classifier) {
        this.classifier = classifier;
    }

    @Override
    public void accept(LogRecord logRecord) {
        T key = classifier.apply(logRecord);
        counts.put(key, counts.getOrDefault(key, 0L) + 1);
    }

    @Override
    public Map<T, Long> getResult() {
        return counts;
    }
}
