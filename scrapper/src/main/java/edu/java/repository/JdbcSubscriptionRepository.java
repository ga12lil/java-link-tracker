package edu.java.repository;

import edu.java.dto.domain.LinkEntity;
import edu.java.dto.domain.SubscriptionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcSubscriptionRepository {
    private final JdbcTemplate jdbcTemplate;

    private final static String FIND_ALL_QUERY = "select chat_id, link_id from subscription";
    private final static String ADD_QUERY = "insert into subscription (chat_id, link_id) values (?, ?)";
    private final static String REMOVE_QUERY = "delete from subscription where chat_id = ? and link_id = ?";
    private final static String COUNT_BY_LINK_ID_QUERY = "select count(*) from subscription where link_id = ?";
    private final static String REMOVE_LINKS_WITHOUT_SUBSCRIBERS_QUERY = """
            delete from link
            where (select count(link_id) from subscription where link_id = link.id) = 0
            """;
    private final static String FIND_LINKS_BY_CHAT_ID_QUERY = """
            select id, url, updated_at
            from link
            join subscription s on link.id = s.link_id
            where chat_id = ?
            """;

    public List<SubscriptionEntity> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, new DataClassRowMapper<>(SubscriptionEntity.class));
    }

    public int add(Long chatId, Long linkId) {
        return jdbcTemplate.update(ADD_QUERY, chatId, linkId);
    }

    public int remove(Long chatId, Long linkId) {
        return jdbcTemplate.update(REMOVE_QUERY, chatId, linkId);
    }

    public int removeLinksWithoutSubscribers() {
        return jdbcTemplate.update(REMOVE_LINKS_WITHOUT_SUBSCRIBERS_QUERY);
    }

    public List<LinkEntity> findLinksByChatId(Long chatId) {
        return jdbcTemplate.query(FIND_LINKS_BY_CHAT_ID_QUERY, new DataClassRowMapper<>(LinkEntity.class), chatId);
    }
    public int countByLinkId(Long id) {
        return jdbcTemplate.queryForObject(COUNT_BY_LINK_ID_QUERY, Integer.class, id);
    }
}
