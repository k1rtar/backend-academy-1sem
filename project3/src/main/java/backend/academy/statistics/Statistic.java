package backend.academy.statistics;

import backend.academy.model.LogRecord;

public interface Statistic<T> {
    void accept(LogRecord logRecord);

    T getResult();
}
