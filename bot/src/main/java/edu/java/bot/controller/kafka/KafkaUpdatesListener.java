package edu.java.bot.controller.kafka;

import edu.java.bot.dto.LinkUpdateRequest;
import edu.java.bot.service.UpdatesService;
import io.micrometer.core.instrument.Counter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaUpdatesListener {
    private final UpdatesService service;
    private final Counter requestsCounter;

    @KafkaListener(topics = "${app.kafka.topic-name}")
    @RetryableTopic(attempts = "3", dltStrategy = DltStrategy.FAIL_ON_ERROR)
    public void listen(LinkUpdateRequest request) {
        service.postUpdate(request);
        log.info("link update from Kafka queue!");
        requestsCounter.increment();
    }

    @DltHandler
    public void handleDlt(LinkUpdateRequest request) {
        //TODO
    }
}
