package edu.java.scrapper.httpclient;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.dto.httpclient.StackExchangeQuestionResponse;
import edu.java.httpclient.StackOverflowClient;
import org.junit.jupiter.api.Test;


import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WireMockTest(httpPort = 8080)
class StackOverflowClientTest extends AbstractTest {

    private final String baseUrl = "http://localhost:8080";
    private final StackOverflowClient stackOverflowClient = new StackOverflowClient(baseUrl);

    @Test
    void shouldReturnResponseWithUpdatedAt() {
        Long id = 5L;

        stubFor(get(format("/%s/%s/%s%s", "2.3", "questions", id, "?site=stackoverflow.com"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(jsonToString("src/test/resources/stackoverflow.json"))));

        StackExchangeQuestionResponse repoResponse = stackOverflowClient.fetchQuestion(id);

        assertEquals(
                OffsetDateTime.ofInstant(Instant.ofEpochSecond(1590400952L),
                        ZoneId.of("UTC")),
                repoResponse.updatedAt());
    }
}