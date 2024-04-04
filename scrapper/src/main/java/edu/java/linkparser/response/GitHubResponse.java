package edu.java.linkparser.response;

public record GitHubResponse(String user, String repository) implements ParseResponse {
}
