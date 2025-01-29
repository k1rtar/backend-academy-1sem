package backend.academy.reader;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

public class UrlLogReaderTest {

    @Test
    public void testReadLines_SuccessfulResponse() throws Exception {
        // Arrange
        URI uri = new URI("http://example.com/logs/access.log");
        String logContent = "Line 1\nLine 2\nLine 3";
        InputStream inputStream = new ByteArrayInputStream(logContent.getBytes(StandardCharsets.UTF_8));

        @SuppressWarnings("unchecked")
        HttpResponse<InputStream> response = mock(HttpResponse.class);
        when(response.body()).thenReturn(inputStream);

        HttpClient httpClient = mock(HttpClient.class);
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
            .thenReturn(response);

        try (MockedStatic<HttpClient> mockedHttpClient = mockStatic(HttpClient.class)) {
            mockedHttpClient.when(HttpClient::newHttpClient).thenReturn(httpClient);

            UrlLogReader reader = new UrlLogReader(uri, StandardCharsets.UTF_8);

            // Act
            Stream<String> lines = reader.readLines();

            // Assert
            assertThat(lines).containsExactly("Line 1", "Line 2", "Line 3");
        }
    }

    @Test
    public void testReadLines_HttpException_ShouldThrowRuntimeException() throws Exception {
        // Arrange
        URI uri = new URI("http://example.com/logs/access.log");

        HttpClient httpClient = mock(HttpClient.class);
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
            .thenThrow(new RuntimeException("HTTP Error"));

        try (MockedStatic<HttpClient> mockedHttpClient = mockStatic(HttpClient.class)) {
            mockedHttpClient.when(HttpClient::newHttpClient).thenReturn(httpClient);

            UrlLogReader reader = new UrlLogReader(uri, StandardCharsets.UTF_8);

            // Act & Assert
            assertThatThrownBy(() -> reader.readLines())
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Ошибка при чтении URL");
        }
    }
}



