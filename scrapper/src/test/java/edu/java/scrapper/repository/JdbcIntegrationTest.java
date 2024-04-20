package edu.java.scrapper.repository;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.service.JdbcChatServiceTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

public class JdbcIntegrationTest extends IntegrationTest {
    @DynamicPropertySource
    static void setJpaAccessType(DynamicPropertyRegistry registry) {
        registry.add("app.database-access-type", JdbcIntegrationTest::getJdbc);
    }

    static String getJdbc() {
        return "jdbc";
    }
}
