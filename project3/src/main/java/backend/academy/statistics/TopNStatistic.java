package backend.academy.statistics;

import backend.academy.model.LogRecord;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TopNStatistic<T> implements Statistic<Map<T, Long>> {
    private final Map<T, Long> counts = new HashMap<>();
    private final int limit;
    private final Function<LogRecord, T> classifier;

    public TopNStatistic(int limit, Function<LogRecord, T> classifier) {
        this.limit = limit;
        this.classifier = classifier;
    }

    @Override
    public void accept(LogRecord logRecord) {
        T key = classifier.apply(logRecord);
        counts.put(key, counts.getOrDefault(key, 0L) + 1);
    }

    @Override
    public Map<T, Long> getResult() {
        return counts.entrySet().stream()
            .sorted(Map.Entry.<T, Long>comparingByValue(Comparator.reverseOrder()))
            .limit(limit)
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (e1, e2) -> e1,
                LinkedHashMap::new
            ));
    }
}
