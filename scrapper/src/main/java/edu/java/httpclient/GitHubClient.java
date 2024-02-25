package edu.java.httpclient;

import edu.java.dto.httpclient.GitHubRepositoryResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

public class GitHubClient {
    private final GitHubHttpExchange gitHubHttpExchange;
    private final static String BASEURL = "https://api.github.com/";

    public GitHubClient() {
        gitHubHttpExchange = factory(BASEURL)
                .createClient(GitHubHttpExchange.class);
    }

    public GitHubClient(String baseUrl) {
        gitHubHttpExchange = factory(baseUrl)
                .createClient(GitHubHttpExchange.class);
    }

    public GitHubRepositoryResponse fetchRepository(String owner, String repo) {
        return gitHubHttpExchange.fetchRepository(owner, repo);
    }

    private HttpServiceProxyFactory factory(String baseUrl) {
        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
        WebClientAdapter adapter = WebClientAdapter.create(webClient);
        return HttpServiceProxyFactory.builderFor(adapter).build();
    }
}
