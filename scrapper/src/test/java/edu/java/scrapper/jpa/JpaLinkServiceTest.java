package edu.java.scrapper.jpa;

import edu.java.dto.mapper.LinkMapper;
import edu.java.repository.jpa.JpaChatRepository;
import edu.java.repository.jpa.JpaLinkRepository;
import edu.java.service.LinkUpdater;
import edu.java.service.jpa.JpaLinkService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JpaLinkServiceTest extends JpaIntegrationTest {
    @Autowired
    private JpaChatRepository chatRepository;
    @Autowired
    private JpaLinkRepository linkRepository;
    @Autowired
    private LinkMapper mapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private LinkUpdater linkUpdater;

    private JpaLinkService linkService;

    @BeforeEach
    public void setUp() {
        linkService = new JpaLinkService( linkRepository, chatRepository, linkUpdater, mapper);
    }

    @Test
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
    @Sql("/sql/add-chat.sql")
    @SneakyThrows
    void removeTest() {
        Long chatId = 1L;
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
