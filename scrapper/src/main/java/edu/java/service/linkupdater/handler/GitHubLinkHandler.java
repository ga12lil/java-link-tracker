package edu.java.service.linkupdater.handler;

import edu.java.httpclient.GitHubClient;
import edu.java.linkparser.response.GitHubResponse;
import edu.java.linkparser.response.ParseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
@RequiredArgsConstructor
public class GitHubLinkHandler implements LinkHandler{
    private final GitHubClient gitHubClient;
    @Override
    public boolean canHandle(ParseResponse parseResponse) {
        return parseResponse.getClass().equals(GitHubResponse.class);
    }

    @Override
    public OffsetDateTime getUpdates(ParseResponse parseResponse) {
        GitHubResponse gitHubResponse = (GitHubResponse) parseResponse;
        var repository = gitHubClient.fetchRepository(gitHubResponse.user(), gitHubResponse.repository());
        return repository.updatedAt();
    }
}
