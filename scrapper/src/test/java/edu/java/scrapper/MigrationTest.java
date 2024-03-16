package edu.java.scrapper;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MigrationTest extends IntegrationTest {
    private static final String GET_ALL_TABLES = """
            SELECT table_name
            FROM information_schema.tables
            WHERE table_schema='public' AND table_catalog = current_database()
            """;
    @Autowired
    private JdbcClient jdbcClient;

    @SneakyThrows
    @Test
    void tablesShouldBeCreated(){
        var tables = jdbcClient.sql(GET_ALL_TABLES)
                .query()
                .singleColumn();

        assertTrue(tables.contains("chat"));
        assertTrue(tables.contains("link"));
        assertTrue(tables.contains("subscription"));
    }
}
