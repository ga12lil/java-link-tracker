package edu.java.bot.configuration;

import edu.java.bot.configuration.httpclient.RetryType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;


@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @NotEmpty
    String telegramToken,
    @NotNull
    URI scrapperPath,
    @NotNull
    RetryType clientRetryType,
    @NotNull
    KafkaConfig kafka
) {
    public record KafkaConfig(@NotNull String bootstrapServer,
                              @NotNull String topicName,
                              @NotNull String groupId,
                              @NotNull int partitionsCount,
                              @NotNull short replicationCount) {}
}
