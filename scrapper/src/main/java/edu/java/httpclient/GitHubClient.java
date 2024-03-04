package edu.java.httpclient;

import edu.java.dto.httpclient.GitHubRepositoryResponse;
import org.springframework.web.reactive.function.client.WebClient;

public class GitHubClient {
    private final WebClient webClient;
    private final static String BASEURL = "https://api.github.com/";

    public GitHubClient() {
        this.webClient = WebClient.create(BASEURL);
    }

    public GitHubClient(String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }

    public GitHubRepositoryResponse fetchRepository(String owner, String repository) {
        return this.webClient
                .get()
                .uri("/repos/{owner}/{repository}", owner, repository)
                .retrieve()
                .bodyToMono(GitHubRepositoryResponse.class)
                .block();
    }
}
