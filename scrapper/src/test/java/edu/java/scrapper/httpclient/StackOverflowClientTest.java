package edu.java.scrapper.httpclient;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.dto.httpclient.StackExchangeQuestionResponse;
import edu.java.httpclient.StackOverflowClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WireMockTest(httpPort = 8080)
class StackOverflowClientTest {

    String baseUrl;
    StackOverflowClient stackOverFlowClient;
    Long id;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:8080";
        stackOverFlowClient = new StackOverflowClient(baseUrl);
        id = 5L;
        String bodyJson = ResponseBodyJson.getStackExchangeResponse();

        stubFor(get("/2.3/questions/" + id + "?site=stackoverflow.com").willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(bodyJson)));
    }

    @Test
    void shouldReturnResponseWithUpdatedAt() {
        StackExchangeQuestionResponse response = stackOverFlowClient.fetchQuestion(id);

        assertEquals(
                OffsetDateTime.ofInstant(Instant.ofEpochSecond(1590400952L), ZoneId.of("UTC")),
                response.updatedAt()
        );
    }


}
