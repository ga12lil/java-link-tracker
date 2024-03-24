package edu.java.scrapper;

import edu.java.dto.domain.ChatEntity;
import edu.java.repository.JdbcChatRepository;
import edu.java.repository.JdbcLinkRepository;
import edu.java.repository.JdbcSubscriptionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JdbcRepositoryTest extends IntegrationTest {
    @Autowired
    private JdbcChatRepository chatRepository;
    @Autowired
    private JdbcLinkRepository linkRepository;
    @Autowired
    private JdbcSubscriptionRepository subscriptionRepository;

    @Test
    @Transactional
    @Rollback
    void chatRepositoryTests() {
        chatRepository.add(5L);
        ChatEntity response = chatRepository.findAll().getFirst();
        assertEquals(response.id(),5L);

        chatRepository.remove(5L);
        assertTrue(chatRepository.findAll().isEmpty());
    }
    // TODO: tests for linkRepository and subscriptions

}
