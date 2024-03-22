package edu.java.service;

import edu.java.dto.domain.LinkEntity;
import edu.java.exception.LinkNotFoundException;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;

public interface LinkService {
    LinkEntity add(long tgChatId, URI url);

    LinkEntity remove(long tgChatId, URI url) throws LinkNotFoundException;

    List<LinkEntity> listAll(long tgChatId);

    List<LinkEntity> findLinksUpdatedBefore(OffsetDateTime dateTime);

    void save(LinkEntity linkEntity);

}
