package edu.java.dto.httpclient;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record StackExchangeQuestionResponse(
        @JsonProperty("last_activity_date")
        OffsetDateTime updatedAt
) {
}
