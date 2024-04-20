package edu.java.scrapper.service;

import edu.java.repository.JdbcLinkRepository;
import edu.java.repository.JdbcSubscriptionRepository;
import edu.java.scrapper.repository.JdbcIntegrationTest;
import edu.java.service.LinkUpdater;
import edu.java.service.jdbc.JdbcLinkService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import java.net.URI;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JdbcLinkServiceTest extends JdbcIntegrationTest {
    private JdbcLinkService linkService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private LinkUpdater linkUpdater;
    @Autowired
    private JdbcLinkRepository linkRepository;
    @Autowired
    private JdbcSubscriptionRepository subscriptionRepository;

    @BeforeEach
    public void setUp() {
        linkService = new JdbcLinkService(linkRepository, subscriptionRepository, linkUpdater);
    }

    @Test
    @Transactional
    @Rollback
    @Sql("/sql/add-five-chats.sql")
    void addTest() {
        Long chatId = 121L;
        String link = "https://github.com/ga12lil/java-course-2023-backend";
        URI url = URI.create(link);

        int before = countSubscriptions(chatId);
        linkService.add(chatId, url);
        int after = countSubscriptions(chatId);
        assertEquals(after - before, 1);

        Long linkId = jdbcTemplate.queryForObject(
                "SELECT link_id FROM subscription WHERE chat_id = ?",
                Long.class, chatId);

        String linkUrl = jdbcTemplate.queryForObject("SELECT url FROM link WHERE id = ?", String.class, linkId);
        assertEquals(linkUrl, link);
    }

    @Test
    @Transactional
    @Rollback
    @Sql("/sql/add-five-chats.sql")
    @SneakyThrows
    void removeTest() {
        Long chatId = 121L;
        String link = "https://github.com/ga12lil/java-course-2023-backend";
        URI url = URI.create(link);
        linkService.add(chatId, url);

        int before = countSubscriptions(chatId);
        linkService.remove(chatId, url);
        int after = countSubscriptions(chatId);

        assertEquals(before - after, 1);

    }

    private int countSubscriptions(Long chatId) {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM subscription WHERE chat_id = ?",
                Integer.class, chatId).intValue();
    }
}
