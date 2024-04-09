package edu.java.scrapper.httpclient;

import lombok.SneakyThrows;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.util.retry.Retry;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

public class AbstractTest {

    @SneakyThrows
    public String jsonToString(String filePath) {
        return Files.readString(Paths.get(filePath));
    }
    public Retry retry(){
        return Retry.fixedDelay(1, Duration.ofSeconds(1))
                .filter(this::isServerError);
    }
    private boolean isServerError(Throwable t) {
        return (t instanceof WebClientResponseException) &&
                ((WebClientResponseException) t).getStatusCode().is5xxServerError();
    }
}
