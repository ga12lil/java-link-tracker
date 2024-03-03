package edu.java.bot.configuration;

import edu.java.bot.httpclient.ScrapperClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ClientConfig {
    @Bean
    ScrapperClient scrapperClient(ApplicationConfig applicationConfig) {
        WebClient client = WebClient.builder()
                .baseUrl(applicationConfig.scrapperPath().toString())
                .build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(client))
                .build();
        return factory.createClient(ScrapperClient.class);
    }
}
