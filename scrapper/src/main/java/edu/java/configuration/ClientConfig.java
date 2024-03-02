package edu.java.configuration;

import edu.java.httpclient.GitHubClient;
import edu.java.httpclient.StackOverflowClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {
    @Bean
    GitHubClient gitHubClient(ApplicationConfig applicationConfig) {
        return new GitHubClient(applicationConfig.api().gitHubApiPath().getPath());
    }

    @Bean
    StackOverflowClient stackOverflowClient(ApplicationConfig applicationConfig) {
        return new StackOverflowClient(applicationConfig.api().stackOverflowApiPath().getPath());
    }
}
