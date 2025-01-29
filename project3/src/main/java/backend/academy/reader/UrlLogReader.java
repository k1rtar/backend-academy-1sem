package backend.academy.reader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.util.stream.Stream;

public class UrlLogReader implements LogReader {
    private final URI uri;
    private final Charset charset;

    public UrlLogReader(URI uri, Charset charset) {
        this.uri = uri;
        this.charset = charset != null ? charset : Charset.defaultCharset();
    }

    @Override
    public Stream<String> readLines() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
            HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(response.body(), charset));
            return reader.lines().onClose(() -> {
                try {
                    reader.close();
                } catch (Exception e) {
                    // Игнорируем
                }
            });
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при чтении URL: " + uri, e);
        }
    }

    @Override
    public void close() throws Exception {
        // Ресурсы закрываются автоматически через try-with-resources
    }
}
