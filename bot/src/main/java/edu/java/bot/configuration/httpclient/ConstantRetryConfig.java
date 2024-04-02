package edu.java.bot.configuration.httpclient;

import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.util.retry.Retry;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "client-retry-type", havingValue = "constant")
@Slf4j
public class ConstantRetryConfig extends AbstractRetryConfig {
    @Bean
    public Retry retry() {
        return Retry.fixedDelay(MAX_RETRY_COUNTS, Duration.ofSeconds(DELAY_SECONDS))
                .filter(this::isServerError)
                .doBeforeRetry(retrySignal -> log.warn("Retrying request after following exception: {}",
                        retrySignal.failure().getLocalizedMessage()));
    }
}
