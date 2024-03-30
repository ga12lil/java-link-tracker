package edu.java.scrapper.service;

import edu.java.scrapper.IntegrationTest;
import edu.java.service.jdbc.JdbcChatService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JdbcChatServiceTest extends IntegrationTest {
    @Autowired
    private JdbcChatService chatService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @Transactional
    @Rollback
    void registerTest() {
        Long chatId = 5L;
        int before = countChatsQuery(chatId);
        chatService.register(chatId);
        int after = countChatsQuery(chatId);
        assertEquals(after - before, 1);
    }

    @Test
    @Transactional
    @Rollback
    @Sql("/sql/add-five-chats.sql")
    @SneakyThrows
    void removeTest() {
        Long chatId = 122L;
        int before = countChatsQuery(chatId);
        chatService.unregister(chatId);
        int after = countChatsQuery(chatId);
        assertEquals(before - after, 1);
    }

    private int countChatsQuery(Long chatId){
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM chat WHERE id = ?", Integer.class, chatId);
    }
}
