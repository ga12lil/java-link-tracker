package edu.java.configuration.databaseAccess;

import edu.java.repository.JdbcChatRepository;
import edu.java.repository.JdbcLinkRepository;
import edu.java.repository.JdbcSubscriptionRepository;
import edu.java.service.ChatService;
import edu.java.service.LinkService;
import edu.java.service.LinkUpdater;
import edu.java.service.jdbc.JdbcChatService;
import edu.java.service.jdbc.JdbcLinkService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfiguration {
    @Bean
    public JdbcLinkRepository linkRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcLinkRepository(jdbcTemplate);
    }

    @Bean
    public JdbcChatRepository chatRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcChatRepository(jdbcTemplate);
    }

    @Bean
    public JdbcSubscriptionRepository subscriptionRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcSubscriptionRepository(jdbcTemplate);
    }

    @Bean
    public LinkService linkService(
            JdbcLinkRepository linkRepository,
            JdbcSubscriptionRepository subscriptionRepository,
            LinkUpdater linkUpdater
    ) {
        return new JdbcLinkService(linkRepository, subscriptionRepository, linkUpdater);
    }

    @Bean
    public ChatService chatService(
            JdbcChatRepository chatRepository,
            JdbcLinkRepository linkRepository,
            JdbcSubscriptionRepository subscriptionRepository
    ) {
        return new JdbcChatService(chatRepository, subscriptionRepository, linkRepository);
    }
}
