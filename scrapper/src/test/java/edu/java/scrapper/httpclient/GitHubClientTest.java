package edu.java.scrapper.httpclient;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.dto.httpclient.GitHubRepositoryResponse;
import edu.java.httpclient.GitHubClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.OffsetDateTime;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WireMockTest(httpPort = 8080)
class GitHubClientTest {

    String baseUrl;
    GitHubClient gitHubClient;
    String owner;
    String repo;

    @BeforeEach
    void start() {
        baseUrl = "http://localhost:8080";
        owner = "owner";
        repo = "repo";
        gitHubClient = new GitHubClient(baseUrl);
        String bodyJson = ResponseBodyJson.getGitHubResponse();

        stubFor(get(format("/%s/%s/%s", "repos", owner, repo))
                .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(bodyJson)));
    }

    @Test
    void shouldReturnResponseWithUpdatedAt() {
        GitHubRepositoryResponse repoResponse = gitHubClient.fetchRepository(owner, repo);

        assertEquals(OffsetDateTime.parse("2024-02-02T14:57:04Z"), repoResponse.updatedAt());
    }


}
