package edu.java.scrapper.jpa;

import edu.java.dto.domain.ChatEntity;
import edu.java.dto.mapper.ChatMapper;
import edu.java.repository.jpa.JpaChatRepository;
import edu.java.repository.jpa.JpaLinkRepository;
import edu.java.scrapper.IntegrationTest;
import edu.java.service.jpa.JpaChatService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JpaChatServiceTest extends JpaIntegrationTest {
    @Autowired
    private JpaChatRepository chatRepository;
    @Autowired
    private JpaLinkRepository linkRepository;
    @Autowired
    private ChatMapper mapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private JpaChatService chatService;

    @BeforeEach
    public void setUp() {
        chatService = new JpaChatService(chatRepository, linkRepository, mapper);
    }
    @Test
    void registerChat() {
        Long chatId = 5L;
        int before = countChatsQuery(chatId);
        chatService.register(chatId);
        int after = countChatsQuery(chatId);
        assertEquals(after - before, 1);
    }

    @Test
    @Sql("/sql/add-five-chats.sql")
    @SneakyThrows
    void removeTest() {
        Long chatId = 122L;
        int before = countChatsQuery(chatId);
        chatService.unregister(chatId);
        int after = countChatsQuery(chatId);
        assertEquals(before - after, 1);
    }

    @Test
    @Sql("/sql/add-subscription.sql")
    @SneakyThrows
    void findByLinkTest() {
        List<ChatEntity> response = chatService.findByLink(URI.create("link"));
        Long chatId = response.getFirst().id();
        assertEquals(chatId, 123L);
    }

    private int countChatsQuery(Long chatId){
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM chat WHERE id = ?", Integer.class, chatId);
    }
}
