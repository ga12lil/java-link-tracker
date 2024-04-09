package edu.java.httpclient;

import edu.java.dto.httpclient.GitHubRepositoryResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

public class GitHubClient {
    private final WebClient webClient;
    private final static String BASEURL = "https://api.github.com/";
    protected Retry retry;

    public GitHubClient(Retry retry) {
        this.webClient = WebClient.create(BASEURL);
        this.retry = retry;
    }

    public GitHubClient(String baseUrl, Retry retry) {
        this.webClient = WebClient.create(baseUrl);
        this.retry = retry;
    }

    public GitHubRepositoryResponse fetchRepository(String owner, String repository) {
        return this.webClient
                .get()
                .uri("/repos/{owner}/{repository}", owner, repository)
                .retrieve()
                .bodyToMono(GitHubRepositoryResponse.class)
                .retryWhen(retry)
                .block();
    }
}
