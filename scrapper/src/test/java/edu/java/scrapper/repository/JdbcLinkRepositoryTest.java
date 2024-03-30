package edu.java.scrapper.repository;

import edu.java.dto.domain.LinkEntity;
import edu.java.repository.JdbcLinkRepository;
import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JdbcLinkRepositoryTest extends IntegrationTest {
    @Autowired
    private JdbcLinkRepository linkRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @Transactional
    @Rollback
    void addTest() {
        String link = "https://link/";
        linkRepository.add(link);
        String query = linkQuery(link);

        assertEquals(link, query);
    }

    @Test
    @Transactional
    @Rollback
    @Sql("/sql/add-two-links.sql")
    void removeTest() {
        String link1 = "link1";
        String link2 = "link2";

        assertEquals(link1, linkQuery(link1));
        assertEquals(link2, linkQuery(link2));

        linkRepository.remove(link1);
        Integer countOfLink1 = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM link WHERE url = ?",
                Integer.class,
                link1);
        assertEquals(countOfLink1, 0);
        assertEquals(link2, linkQuery(link2));
    }

    @Test
    @Transactional
    @Rollback
    @Sql("/sql/add-two-links.sql")
    void findTest() {
        String link1 = "link1";
        String link2 = "link2";

        assertEquals(link1, linkRepository.find(link1).get().url());
        assertEquals(link2, linkRepository.find(link2).get().url());
    }

    @Test
    @Transactional
    @Rollback
    @Sql("/sql/add-two-links.sql")
    void saveTest(){
        String link1 = "link1";
        assertEquals(link1, linkQuery(link1));

        LinkEntity oldLink = linkRepository.find(link1).get();
        OffsetDateTime time = OffsetDateTime.now();
        LinkEntity updatedLink = new LinkEntity(oldLink.id(), oldLink.url(), time, time);
        linkRepository.save(updatedLink);
        LinkEntity query = jdbcTemplate.queryForObject(
                "SELECT id, url, updated_at, last_check from link WHERE url = ?",
                new DataClassRowMapper<>(LinkEntity.class),
                link1);

        assertEquals(
                query.updatedAt().atZoneSameInstant(ZoneOffset.UTC).getSecond(),
                time.atZoneSameInstant(ZoneOffset.UTC).getSecond()
        );
        assertEquals(
                query.lastCheck().atZoneSameInstant(ZoneOffset.UTC).getSecond(),
                time.atZoneSameInstant(ZoneOffset.UTC).getSecond()
        );
    }

    private String linkQuery(String url) {
        return jdbcTemplate.queryForObject("SELECT url from link WHERE url = ?", String.class, url);
    }
}
