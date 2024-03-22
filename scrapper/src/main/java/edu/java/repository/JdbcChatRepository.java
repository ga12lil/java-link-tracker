package edu.java.repository;

import edu.java.dto.domain.ChatEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcChatRepository {
    private final JdbcTemplate jdbcTemplate;

    private final static String FIND_ALL_QUERY = "select id from chat";
    private final static String ADD_QUERY = "insert into chat (id) values (?)";
    private final static String REMOVE_QUERY = "delete from chat where id = ?";

    public List<ChatEntity> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, new DataClassRowMapper<>(ChatEntity.class));
    }

    public int add(Long id) {
        return jdbcTemplate.update(ADD_QUERY, id);
    }

    public int remove(Long id) {
        return jdbcTemplate.update(REMOVE_QUERY, id);
    }
}
