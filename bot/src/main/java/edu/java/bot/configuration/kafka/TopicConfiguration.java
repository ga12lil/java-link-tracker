package edu.java.bot.configuration.kafka;

import edu.java.bot.configuration.ApplicationConfig;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
@EnableKafka
@RequiredArgsConstructor
public class TopicConfiguration {
    private final ApplicationConfig config;

    @Bean
    public NewTopic messagesTopic() {
        return new NewTopic(
                config.kafka().topicName() + "_dlq",
                config.kafka().partitionsCount(),
                config.kafka().replicationCount()
        );
    }
}
