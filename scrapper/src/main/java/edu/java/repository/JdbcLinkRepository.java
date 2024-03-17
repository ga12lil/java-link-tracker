package edu.java.repository;

import edu.java.dto.domain.LinkEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcLinkRepository {
    private final JdbcTemplate jdbcTemplate;

    private final static String FIND_ALL_QUERY = "select id, url, updated_at from link";
    private final static String ADD_QUERY = "insert into link (url) values (?)";
    private final static String REMOVE_QUERY = "delete from link where url = ?";
    private final static String SAVE_QUERY = "update link set url=?, updated_at=? where id=?";
    private final static String FIND_QUERY = """
            select id, url, updated_at
            from link
            where url = ?
            """;
    private final static String FIND_BY_ID_QUERY = """
            select id, url, updated_at
            from link
            where id = ?
            """;

    public List<LinkEntity> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, new DataClassRowMapper<>(LinkEntity.class));
    }

    public Long add(String url) {
        return (long)jdbcTemplate.update(ADD_QUERY, url);
    }

    public int remove(String url) {
        return jdbcTemplate.update(REMOVE_QUERY, url);
    }

    public Optional<LinkEntity> find(String url) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_QUERY, new DataClassRowMapper<>(LinkEntity.class), url));
        }
        catch(EmptyResultDataAccessException ex){
            return Optional.empty();
        }
    }
    public Optional<LinkEntity> findById(Long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID_QUERY, new DataClassRowMapper<>(LinkEntity.class), id));
    }

    public int save(LinkEntity link) {
        return jdbcTemplate.update(SAVE_QUERY, link.url(), link.updatedAt(), link.id());
    }
}
