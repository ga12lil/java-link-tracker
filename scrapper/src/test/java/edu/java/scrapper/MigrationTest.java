package edu.java.scrapper;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class MigrationTest extends IntegrationTest {
    private static final String GET_ALL_TABLES = """
            SELECT table_name
            FROM information_schema.tables
            WHERE table_schema='public' AND table_catalog = current_database()
            """;

    @SneakyThrows
    @Test
    void tablesShouldBeCreated(){
        Connection connection = DriverManager.getConnection(
                POSTGRES.getJdbcUrl(),
                POSTGRES.getUsername(),
                POSTGRES.getPassword()
        );
        ResultSet allTables = connection.createStatement().executeQuery(GET_ALL_TABLES);

        assertThat(allTablesToList(allTables)).containsAll(necessaryTables());
    }

    private List<String> allTablesToList(ResultSet allTables) throws SQLException {
        List<String> result = new ArrayList<>();
        while(allTables.next()){
            result.add(allTables.getString("TABLE_NAME"));
        }
        return result;
    }

    private List<String> necessaryTables(){
        return List.of("link", "chat", "subscription");
    }
}
