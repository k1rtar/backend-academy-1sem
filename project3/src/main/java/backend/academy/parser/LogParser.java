package backend.academy.parser;

import backend.academy.model.LogRecord;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParser {
    private static final String LOG_PATTERN =
        "^([\\d.:a-fA-F]+) - (\\S+) \\[([^\\]]+)\\] \"(\\S+) ([^\\s\"]+) (\\S+)\" "
            + "(\\d{3}) (\\d+) \"([^\"]*)\" \"([^\"]*)\"$";
    private static final Pattern PATTERN = Pattern.compile(LOG_PATTERN);
    private static final DateTimeFormatter DATE_FORMATTER =
        DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);
    private static final int REMOTE_ADDR_GROUP = 1;
    private static final int REMOTE_USER_GROUP = 2;
    private static final int TIME_LOCAL_GROUP = 3;
    private static final int METHOD_GROUP = 4;
    private static final int RESOURCE_GROUP = 5;
    private static final int PROTOCOL_GROUP = 6;
    private static final int STATUS_GROUP = 7;
    private static final int BODY_BYTES_SENT_GROUP = 8;
    private static final int HTTP_REFERER_GROUP = 9;
    private static final int HTTP_USER_AGENT_GROUP = 10;

    @SuppressWarnings("squid:S106")
    public LogRecord parse(String line) {
        String lineContent = line;
        if (lineContent.startsWith("\uFEFF")) {
            lineContent = lineContent.substring(1);
        }
        Matcher matcher = PATTERN.matcher(lineContent);
        if (!matcher.matches()) {
            System.err.println("Некорректная строка лога (будет пропущена): " + lineContent);
            return null;
        }

        String remoteAddr = matcher.group(REMOTE_ADDR_GROUP);
        String remoteUser = matcher.group(REMOTE_USER_GROUP);
        String timeLocalStr = matcher.group(TIME_LOCAL_GROUP);
        String method = matcher.group(METHOD_GROUP);
        String resource = matcher.group(RESOURCE_GROUP);
        String protocol = matcher.group(PROTOCOL_GROUP);
        int status = Integer.parseInt(matcher.group(STATUS_GROUP));
        long bodyBytesSent = Long.parseLong(matcher.group(BODY_BYTES_SENT_GROUP));
        String httpReferer = matcher.group(HTTP_REFERER_GROUP);
        String httpUserAgent = matcher.group(HTTP_USER_AGENT_GROUP);

        LocalDateTime timeLocal = LocalDateTime.parse(timeLocalStr, DATE_FORMATTER);

        return LogRecord.builder()
            .remoteAddr(remoteAddr)
            .remoteUser(remoteUser)
            .timeLocal(timeLocal)
            .method(method)
            .resource(resource)
            .protocol(protocol)
            .status(status)
            .bodyBytesSent(bodyBytesSent)
            .httpReferer(httpReferer)
            .httpUserAgent(httpUserAgent)
            .build();
    }
}
