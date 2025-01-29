package backend.academy.statistics;

import backend.academy.model.LogRecord;

public class AverageResponseSizeStatistic implements Statistic<Double> {
    private long count = 0;
    private double sum = 0;

    @Override
    public void accept(LogRecord logRecord) {
        sum += logRecord.getBodyBytesSent();
        count++;
    }

    @Override
    public Double getResult() {
        return count == 0 ? 0 : sum / count;
    }
}
