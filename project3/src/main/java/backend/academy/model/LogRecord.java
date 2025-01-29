package backend.academy.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@Builder
public class LogRecord {
    private String remoteAddr;
    private String remoteUser;
    private LocalDateTime timeLocal;
    private String method;
    private String resource;
    private String protocol;
    private int status;
    private long bodyBytesSent;
    private String httpReferer;
    private String httpUserAgent;
}
