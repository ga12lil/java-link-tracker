package edu.java.service.jdbc;

import edu.java.dto.domain.ChatEntity;
import edu.java.dto.domain.LinkEntity;
import edu.java.exception.ChatNotFoundException;
import edu.java.exception.LinkNotFoundException;
import edu.java.repository.JdbcChatRepository;
import edu.java.repository.JdbcLinkRepository;
import edu.java.repository.JdbcSubscriptionRepository;
import edu.java.service.ChatService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class JdbcChatService  implements ChatService {
    private final JdbcChatRepository chatRepository;
    private final JdbcSubscriptionRepository subscriptionRepository;
    private final JdbcLinkRepository linkRepository;

    @Override
    @Transactional
    public void register(long tgChatId) {
        chatRepository.add(tgChatId);
    }

    @Override
    @Transactional
    public void unregister(long tgChatId) throws ChatNotFoundException {
        int removedRows = chatRepository.remove(tgChatId);
        if (removedRows == 0) {
            throw new ChatNotFoundException(tgChatId);
        }
        subscriptionRepository.removeLinksWithoutSubscribers();
    }

    @Override
    @Transactional
    public List<ChatEntity> findByLink(URI url) throws LinkNotFoundException {
        LinkEntity link = linkRepository.find(url.toString()).orElseThrow(() -> new LinkNotFoundException(url));
        return subscriptionRepository.findChatsByLinkId(link.id());
    }
}
