package edu.java.httpclient.botclient;

import edu.java.dto.bot.LinkUpdateRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange
public interface BotClient {
    @PostExchange("/updates")
    void postUpdate(@RequestBody LinkUpdateRequest linkUpdateRequest);
}
