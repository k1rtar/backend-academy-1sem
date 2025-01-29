package backend.academy.statistics;

import backend.academy.config.Config;
import backend.academy.model.LogRecord;
import backend.academy.model.LogReport;
import java.util.Collections;
import java.util.stream.Stream;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class StatisticsCollector {

    private static final double PERCENTILE_95 = 95.0;
    private static final int TOP_N = 10;

    public LogReport collect(Stream<LogRecord> logRecords, Config config) {
        TotalRequestsStatistic totalRequestsStat = new TotalRequestsStatistic();
        AverageResponseSizeStatistic avgResponseSizeStat = new AverageResponseSizeStatistic();
        PercentileResponseSizeStatistic percentile95Stat = new PercentileResponseSizeStatistic(PERCENTILE_95);
        TopNStatistic<String> topResourcesStat = new TopNStatistic<>(TOP_N, LogRecord::getResource);
        TopNStatistic<String> topClientsStat = new TopNStatistic<>(TOP_N, LogRecord::getRemoteAddr);
        CountByFieldStatistic<Integer> statusCodesStat = new CountByFieldStatistic<>(LogRecord::getStatus);
        CountByFieldStatistic<String> requestMethodsStat = new CountByFieldStatistic<>(LogRecord::getMethod);

        logRecords.forEach(logRecord -> {
            totalRequestsStat.accept(logRecord);
            avgResponseSizeStat.accept(logRecord);
            percentile95Stat.accept(logRecord);
            topResourcesStat.accept(logRecord);
            topClientsStat.accept(logRecord);
            statusCodesStat.accept(logRecord);
            requestMethodsStat.accept(logRecord);
        });

        if (totalRequestsStat.getResult() == 0) {
            log.warn("Предупреждение: Нет записей для анализа в заданном временном диапазоне.");
            return LogReport.builder()
                .files(config.getPaths().toArray(new String[0]))
                .startDate(config.getFrom())
                .endDate(config.getTo())
                .totalRequests(0)
                .averageResponseSize(0)
                .percentile95ResponseSize(0)
                .topResources(Collections.emptyMap())
                .statusCodes(Collections.emptyMap())
                .requestMethods(Collections.emptyMap())
                .topClients(Collections.emptyMap())
                .build();
        }

        return LogReport.builder()
            .files(config.getPaths().toArray(new String[0]))
            .startDate(config.getFrom())
            .endDate(config.getTo())
            .totalRequests(totalRequestsStat.getResult())
            .averageResponseSize(avgResponseSizeStat.getResult())
            .percentile95ResponseSize(percentile95Stat.getResult())
            .topResources(topResourcesStat.getResult())
            .statusCodes(statusCodesStat.getResult())
            .requestMethods(requestMethodsStat.getResult())
            .topClients(topClientsStat.getResult())
            .build();
    }
}
