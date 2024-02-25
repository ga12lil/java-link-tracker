package edu.java.dto.httpclient;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record StackExchangeResponse(
        @JsonProperty("items")
        List<StackExchangeQuestionResponse> questions
) {
}
