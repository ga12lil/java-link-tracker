package edu.java.scrapper.httpclient;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.dto.httpclient.GitHubRepositoryResponse;
import edu.java.httpclient.GitHubClient;
import org.junit.jupiter.api.Test;
import java.time.OffsetDateTime;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WireMockTest(httpPort = 8080)
class GitHubClientTest extends AbstractTest {

    private final String baseUrl = "http://localhost:8080";
    private final GitHubClient gitHubClient = new GitHubClient(baseUrl);

    @Test
    void shouldReturnResponseWithUpdatedAt() {
        String owner = "owner";
        String repo = "repo";

        stubFor(get(format("/%s/%s/%s", "repos", owner, repo))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(jsonToString("src/test/resources/github.json"))));

        GitHubRepositoryResponse repoResponse = gitHubClient.fetchRepository(owner, repo);

        assertEquals(OffsetDateTime.parse("2024-02-02T14:57:04Z"), repoResponse.updatedAt());
    }
}