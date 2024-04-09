package edu.java.kafka;

import edu.java.dto.bot.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScrapperQueueProducer {
    public final KafkaTemplate<String, LinkUpdateRequest> kafkaTemplate;
    public final NewTopic messagesTopic;
    public void send(LinkUpdateRequest update) {
        kafkaTemplate.send(messagesTopic.name(), update);
    }
}
