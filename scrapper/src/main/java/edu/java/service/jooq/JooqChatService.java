package edu.java.service.jooq;

import edu.java.exception.ChatNotFoundException;
import edu.java.repository.jooq.JooqChatRepository;
import edu.java.repository.jooq.JooqSubscriptionRepository;
import edu.java.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JooqChatService implements ChatService {
    private final JooqChatRepository jooqChatRepository;
    private final JooqSubscriptionRepository jooqSubscriptionRepository;


    @Override
    @Transactional
    public void register(long id) {
        jooqChatRepository.add(id);
    }

    @Override
    @Transactional
    public void unregister(long id) throws ChatNotFoundException {
        int removedRows = jooqChatRepository.removeById(id);
        if (removedRows == 0) {
            throw new ChatNotFoundException(id);
        }

        jooqSubscriptionRepository.removeLinksWithoutSubscribers();
    }
}
