package backend.academy.statistics;

import backend.academy.model.LogRecord;

public class TotalRequestsStatistic implements Statistic<Long> {
    private long totalRequests = 0;

    @Override
    public void accept(LogRecord logRecord) {
        totalRequests++;
    }

    @Override
    public Long getResult() {
        return totalRequests;
    }
}
