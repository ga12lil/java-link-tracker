package edu.java.service.jooq;

import edu.java.dto.domain.LinkEntity;
import edu.java.exception.LinkNotFoundException;
import edu.java.repository.jooq.JooqLinkRepository;
import edu.java.repository.jooq.JooqSubscriptionRepository;
import edu.java.service.LinkService;
import edu.java.service.LinkUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JooqLinkService implements LinkService {
    private final JooqSubscriptionRepository subscriptionRepository;
    private final JooqLinkRepository linkRepository;
    private final LinkUpdater linkUpdater;

    @Override
    @Transactional
    public LinkEntity add(long chatId, URI url) {
        LinkEntity link = linkRepository.find(url.toString())
                .orElseGet(() -> linkRepository.add(url.toString()));

        LinkEntity updatedLink = linkUpdater.update(link);
        linkRepository.save(updatedLink);
        subscriptionRepository.add(chatId, updatedLink.id());

        return updatedLink;
    }

    @Override
    @Transactional
    public LinkEntity remove(long chatId, URI url) throws LinkNotFoundException {
        LinkEntity link = linkRepository.find(url.toString())
                .orElseThrow(() -> new LinkNotFoundException(url));

        subscriptionRepository.remove(chatId, link.id());
        if (subscriptionRepository.countByLinkId(link.id()) == 0) {
            linkRepository.removeById(link.id());
        }

        return link;
    }

    @Override
    @Transactional
    public List<LinkEntity> listAll(long tgChatId) {
        return subscriptionRepository.findLinksByChatId(tgChatId);
    }

    @Override
    @Transactional
    public void save(LinkEntity linkEntity) {
        linkRepository.save(linkEntity);
    }

    @Override
    public List<LinkEntity> findLinksUpdatedBefore(OffsetDateTime dateTime) {
        return linkRepository.findLinksUpdatedBefore(dateTime);
    }
}

