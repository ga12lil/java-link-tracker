package edu.java.configuration;

import edu.java.dto.bot.LinkUpdateRequest;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
@EnableKafka
@RequiredArgsConstructor
public class KafkaConfig {
    private final ApplicationConfig config;

    @Bean
    public KafkaTemplate<String, LinkUpdateRequest> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    private ProducerFactory<String, LinkUpdateRequest> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                config.kafka().bootstrapServer()
        );
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class
        );
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class
        );
        configProps.put(
                JsonSerializer.ADD_TYPE_INFO_HEADERS,
                false
        );

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public NewTopic messagesTopic() {
        return new NewTopic(
                config.kafka().topicName(),
                config.kafka().partitionsCount(),
                config.kafka().replicationCount()
        );
    }
}
