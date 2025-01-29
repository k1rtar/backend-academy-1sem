package backend.academy.reader;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;
import lombok.experimental.UtilityClass;
import org.apache.commons.validator.routines.UrlValidator;

@UtilityClass
public class LogReaderFactory {

    public static LogReader create(List<String> paths, Charset charset) {
        if (paths.size() == 1) {
            String path = paths.get(0);
            try {
                UrlValidator urlValidator = new UrlValidator(new String[]{"http", "https"});
                if (urlValidator.isValid(path)) {
                    return new UrlLogReader(new URI(path), charset);
                }
            } catch (URISyntaxException e) {
                // Не является URL, продолжаем как с файлами
            }
        }
        return new FileLogReader(paths, charset);
    }
}
