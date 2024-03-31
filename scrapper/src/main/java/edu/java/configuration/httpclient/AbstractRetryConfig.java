package edu.java.configuration.httpclient;

import org.springframework.web.reactive.function.client.WebClientResponseException;

public class AbstractRetryConfig {
    public static final int MAX_RETRY_COUNTS = 5;
    public static final int DELAY_SECONDS = 5;

    protected boolean isServerError(Throwable t) {
        return (t instanceof WebClientResponseException) &&
                ((WebClientResponseException) t).getStatusCode().is5xxServerError();
    }
}
