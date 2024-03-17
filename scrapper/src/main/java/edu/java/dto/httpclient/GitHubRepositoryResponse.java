package edu.java.dto.httpclient;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record GitHubRepositoryResponse(
        @JsonProperty("pushed_at")
        OffsetDateTime updatedAt
) {
}
