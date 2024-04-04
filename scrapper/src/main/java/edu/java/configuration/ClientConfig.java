package edu.java.configuration;

import edu.java.httpclient.GitHubClient;
import edu.java.httpclient.StackOverflowClient;
import edu.java.httpclient.botclient.BotClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ClientConfig {
    @Bean
    GitHubClient gitHubClient(ApplicationConfig applicationConfig) {
        return new GitHubClient(applicationConfig.api().gitHubApiPath().toString());
    }

    @Bean
    StackOverflowClient stackOverflowClient(ApplicationConfig applicationConfig) {
        return new StackOverflowClient(applicationConfig.api().stackOverflowApiPath().toString());
    }

    @Bean
    BotClient botClient(ApplicationConfig applicationConfig) {
        WebClient client = WebClient.builder()
                .baseUrl(applicationConfig.api().botPath().toString())
                .build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(client))
                .build();
        return factory.createClient(BotClient.class);
    }
}
