package edu.java.scrapper.jpa;

import edu.java.scrapper.IntegrationTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

public class JpaIntegrationTest extends IntegrationTest {

    @DynamicPropertySource
    static void setJpaAccessType(DynamicPropertyRegistry registry) {
        registry.add("app.database-access-type", JpaIntegrationTest::getJpa);
    }

    static String getJpa() {
        return "jpa";
    }
}
