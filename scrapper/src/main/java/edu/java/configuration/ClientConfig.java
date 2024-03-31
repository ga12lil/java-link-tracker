package edu.java.configuration;

import edu.java.httpclient.GitHubClient;
import edu.java.httpclient.StackOverflowClient;
import edu.java.httpclient.botclient.BotClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Configuration
@RequiredArgsConstructor
public class ClientConfig {
    private final Retry retry;
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
                .filter(withRetryableRequests())
                .build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(client))
                .build();
        return factory.createClient(BotClient.class);
    }

    private ExchangeFilterFunction withRetryableRequests() {
        return (request, next) -> next.exchange(request)
                .flatMap(clientResponse -> Mono.just(clientResponse)
                        .filter(response ->  clientResponse.statusCode().isError())
                        .flatMap(response -> clientResponse.createException())
                        .flatMap(Mono::error)
                        .thenReturn(clientResponse))
                .retryWhen(retry);
    }
}
