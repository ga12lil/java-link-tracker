package edu.java.service.jdbc;

import edu.java.exception.ChatNotFoundException;
import edu.java.repository.JdbcChatRepository;
import edu.java.repository.JdbcSubscriptionRepository;
import edu.java.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JdbcChatService  implements ChatService {
    private final JdbcChatRepository chatRepository;
    private final JdbcSubscriptionRepository subscriptionRepository;

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
}
