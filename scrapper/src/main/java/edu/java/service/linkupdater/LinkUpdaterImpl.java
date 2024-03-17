package edu.java.service.linkupdater;

import edu.java.dto.domain.LinkEntity;
import edu.java.linkparser.LinkParser;
import edu.java.linkparser.response.ParseResponse;
import edu.java.service.LinkUpdater;
import edu.java.service.linkupdater.handler.LinkHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class LinkUpdaterImpl implements LinkUpdater {
    private final LinkParser linkParser;
    private final LinkHandlerChain linkHandlerChain;

    @Override
    public LinkEntity update(LinkEntity link) {
        ParseResponse parseResponse = linkParser.parse(URI.create(link.url()));
        LinkHandler handler = linkHandlerChain.getHandler(parseResponse);
        OffsetDateTime currentUpdatedAtTime = handler.getUpdates(parseResponse);


        return new LinkEntity(link.id(), link.url(), currentUpdatedAtTime);
    }
}
