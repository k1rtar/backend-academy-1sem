
package backend.academy.statistics;

import static org.assertj.core.api.Assertions.*;

import backend.academy.model.LogRecord;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

public class PercentileResponseSizeStatisticTest {

    @Test
    public void testGetResult_WithRecords() {
        // Arrange
        PercentileResponseSizeStatistic statistic = new PercentileResponseSizeStatistic(95.0);

        LogRecord record1 = LogRecord.builder().bodyBytesSent(100).build();
        LogRecord record2 = LogRecord.builder().bodyBytesSent(200).build();
        LogRecord record3 = LogRecord.builder().bodyBytesSent(300).build();
        LogRecord record4 = LogRecord.builder().bodyBytesSent(400).build();
        LogRecord record5 = LogRecord.builder().bodyBytesSent(500).build();

        // Act
        statistic.accept(record1);
        statistic.accept(record2);
        statistic.accept(record3);
        statistic.accept(record4);
        statistic.accept(record5);
        Double result = statistic.getResult();

        // Assert
        // 95-й перцентиль для 5 элементов: 0.95 * 5 = 4.75 => округление вверх до 5-го элемента => 500
        assertThat(result).isCloseTo(500.0, Offset.offset(0.1));
    }

    @Test
    public void testGetResult_NoRecords() {
        // Arrange
        PercentileResponseSizeStatistic statistic = new PercentileResponseSizeStatistic(95.0);

        // Act
        Double result = statistic.getResult();

        // Assert
        assertThat(result).isEqualTo(0.0);
    }
}
