package edu.java.service.linkupdater;

import edu.java.linkparser.response.ParseResponse;
import edu.java.service.linkupdater.handler.LinkHandler;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LinkHandlerChain {
    private final List<LinkHandler> linkHandlers;

    public LinkHandler getHandler(ParseResponse parseResponse) {
        return linkHandlers.stream()
                .filter(handler -> handler.canHandle(parseResponse))
                .findFirst().orElseThrow();
    }
}
