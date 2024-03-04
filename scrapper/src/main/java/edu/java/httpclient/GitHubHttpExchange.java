package edu.java.httpclient;

import edu.java.dto.httpclient.GitHubRepositoryResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(url = "/repos/{owner}/{repo}", accept = "application/vnd.github.v3+json")
public interface GitHubHttpExchange {
    @GetExchange
    GitHubRepositoryResponse fetchRepository(@PathVariable String owner, @PathVariable String repo);
}
