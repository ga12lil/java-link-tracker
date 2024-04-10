package edu.java.configuration;

import edu.java.configuration.httpclient.RetryType;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @NotNull
    Scheduler scheduler,
    @NotNull
    Api api,
    @NotNull
    KafkaConfig kafka,
    @NotNull
    RetryType clientRetryType,
    @NotNull
    AccessType databaseAccessType,
    @NotNull
    boolean useQueue

) {
    public record Scheduler(boolean enable, @NotNull Duration interval, @NotNull Duration forceCheckDelay) {
    }

    public record Api(@NotNull URI gitHubApiPath, @NotNull URI stackOverflowApiPath, @NotNull URI botPath) {}

    public record KafkaConfig(@NotNull String bootstrapServer,
                              @NotNull String topicName,
                              @NotNull int partitionsCount,
                              @NotNull short replicationCount) {}
}
