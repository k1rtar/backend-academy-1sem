package backend.academy.statistics;

import backend.academy.model.LogRecord;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;

public class PercentileResponseSizeStatistic implements Statistic<Double> {
    private final List<Double> values = new ArrayList<>();
    private final double percentile;

    public PercentileResponseSizeStatistic(double percentile) {
        this.percentile = percentile;
    }

    @Override
    public void accept(LogRecord logRecord) {
        values.add((double) logRecord.getBodyBytesSent());
    }

    @Override
    public Double getResult() {
        if (values.isEmpty()) {
            return 0.0;
        }
        double[] array = values.stream().mapToDouble(Double::doubleValue).toArray();
        return new Percentile().evaluate(array, percentile);
    }
}
