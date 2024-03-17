package edu.java.service.jdbc;

import edu.java.dto.domain.LinkEntity;
import edu.java.exception.LinkNotFoundException;
import edu.java.repository.JdbcLinkRepository;
import edu.java.repository.JdbcSubscriptionRepository;
import edu.java.service.LinkService;
import edu.java.service.LinkUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JdbcLinkService implements LinkService{
    private final JdbcLinkRepository linkRepository;
    private final JdbcSubscriptionRepository subscriptionRepository;
    private final LinkUpdater linkUpdater;
    @Override
    public LinkEntity add(long tgChatId, URI url) {
        LinkEntity link = linkRepository.find(url.toString()).orElseGet(() -> {
            Long linkId = linkRepository.add(url.toString());
            return linkRepository.findById(linkId).orElseThrow();
        });

        LinkEntity updatedLink = linkUpdater.update(link);
        linkRepository.save(updatedLink);
        subscriptionRepository.add(tgChatId, updatedLink.id());

        return updatedLink;
    }

    @Override
    public LinkEntity remove(long tgChatId, URI url) throws LinkNotFoundException {
        LinkEntity link = linkRepository.find(url.toString())
                .orElseThrow(() -> new LinkNotFoundException(url));

        subscriptionRepository.remove(tgChatId, link.id());
        if (subscriptionRepository.countByLinkId(link.id()) == 0) {
            linkRepository.remove(link.url());
        }

        return link;
    }

    @Override
    public List<LinkEntity> listAll(long tgChatId) {
        return subscriptionRepository.findLinksByChatId(tgChatId);
    }
}
