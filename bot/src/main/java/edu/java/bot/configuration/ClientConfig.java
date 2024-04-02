package edu.java.bot.configuration;

import edu.java.bot.httpclient.ScrapperClient;
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
    ScrapperClient scrapperClient(ApplicationConfig applicationConfig) {
        return scrapperClient(applicationConfig.scrapperPath().toString());
    }

    public ScrapperClient scrapperClient(String scrapperPath) {
        WebClient client = WebClient.builder()
                .baseUrl(scrapperPath)
                .filter(withRetryableRequests())
                .build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(client))
                .build();
        return factory.createClient(ScrapperClient.class);
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
