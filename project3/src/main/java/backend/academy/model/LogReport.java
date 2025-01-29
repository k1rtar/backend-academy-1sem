package backend.academy.model;

import java.time.LocalDateTime;
import java.util.Map;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;



@Getter
@ToString
@EqualsAndHashCode
@Builder
public class LogReport {
    private String[] files;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private long totalRequests;
    private double averageResponseSize;
    private double percentile95ResponseSize;
    private Map<String, Long> topResources;
    private Map<Integer, Long> statusCodes;
    private final Map<String, Long> requestMethods;
    private final Map<String, Long> topClients;
}
