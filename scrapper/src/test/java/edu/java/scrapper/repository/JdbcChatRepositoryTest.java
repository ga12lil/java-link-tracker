package edu.java.scrapper.repository;

import edu.java.repository.JdbcChatRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JdbcChatRepositoryTest extends JdbcIntegrationTest {
    @Autowired
    private JdbcChatRepository chatRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @Transactional
    @Rollback
    void addTest() {
        int before = countChatsQuery();
        chatRepository.add(999L);
        int after = countChatsQuery();
        assertEquals(after - before, 1);
    }

    @Test
    @Transactional
    @Rollback
    @Sql("/sql/add-five-chats.sql")
    void removeTest() {
        int before = countChatsQuery();
        for (int i = 121; i <= 125; i++){
            chatRepository.remove((long) i);
        }
        int after = countChatsQuery();
        assertEquals(before - after, 5);
    }

    private int countChatsQuery(){
        return jdbcTemplate.queryForObject("SELECT COUNT(*) from chat", Integer.class);
    }

}
