package edu.java.kafka;

import edu.java.dto.bot.LinkUpdateRequest;
import edu.java.httpclient.botclient.BotClient;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app", name = "useQueue", havingValue = "true")
public class ScrapperQueueProducer implements BotClient {
    public final KafkaTemplate<String, LinkUpdateRequest> kafkaTemplate;
    public final NewTopic messagesTopic;

    public void postUpdate(LinkUpdateRequest update) {
        kafkaTemplate.send(messagesTopic.name(), update);
    }
}
