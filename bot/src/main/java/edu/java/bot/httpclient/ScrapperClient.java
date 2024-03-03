package edu.java.bot.httpclient;

import edu.java.bot.dto.scrapper.AddLinkRequest;
import edu.java.bot.dto.scrapper.LinkResponse;
import edu.java.bot.dto.scrapper.ListLinksResponse;
import edu.java.bot.dto.scrapper.RemoveLinkRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange
public interface ScrapperClient {
    @GetExchange("/links")
    ListLinksResponse getAllLinks(@RequestHeader(value = "Tg-Chat-Id") Long tgChatId);

    @PostExchange("/links")
    LinkResponse addLink(@RequestHeader(value = "Tg-Chat-Id") Long tgChatId, @RequestBody AddLinkRequest request);

    @DeleteExchange("/links")
    LinkResponse removeLink(@RequestHeader(value = "Tg-Chat-Id") Long tgChatId, @RequestBody RemoveLinkRequest request);

    @PostExchange("/tg-chat/{id}")
    void registerChat(@PathVariable Long id);

    @DeleteExchange("/tg-chat/{id}")
    void removeChat(@PathVariable Long id);
}
