
package backend.academy.statistics;

import static org.assertj.core.api.Assertions.*;

import backend.academy.model.LogRecord;
import org.junit.jupiter.api.Test;

public class AverageResponseSizeStatisticTest {

    @Test
    public void testGetResult_WithRecords() {
        // Arrange
        AverageResponseSizeStatistic statistic = new AverageResponseSizeStatistic();

        LogRecord record1 = LogRecord.builder().bodyBytesSent(500).build();
        LogRecord record2 = LogRecord.builder().bodyBytesSent(1500).build();

        // Act
        statistic.accept(record1);
        statistic.accept(record2);
        Double result = statistic.getResult();

        // Assert
        assertThat(result).isEqualTo(1000.0);
    }

    @Test
    public void testGetResult_NoRecords() {
        // Arrange
        AverageResponseSizeStatistic statistic = new AverageResponseSizeStatistic();

        // Act
        Double result = statistic.getResult();

        // Assert
        assertThat(result).isEqualTo(0.0);
    }
}
