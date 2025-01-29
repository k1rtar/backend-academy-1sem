
package backend.academy.statistics;

import static org.assertj.core.api.Assertions.*;

import backend.academy.model.LogRecord;
import org.junit.jupiter.api.Test;

public class TotalRequestsStatisticTest {

    @Test
    public void testGetResult_WithRecords() {
        // Arrange
        TotalRequestsStatistic statistic = new TotalRequestsStatistic();

        LogRecord record1 = LogRecord.builder().build();
        LogRecord record2 = LogRecord.builder().build();

        // Act
        statistic.accept(record1);
        statistic.accept(record2);
        Long result = statistic.getResult();

        // Assert
        assertThat(result).isEqualTo(2L);
    }

    @Test
    public void testGetResult_NoRecords() {
        // Arrange
        TotalRequestsStatistic statistic = new TotalRequestsStatistic();

        // Act
        Long result = statistic.getResult();

        // Assert
        assertThat(result).isEqualTo(0L);
    }
}
