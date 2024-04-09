package edu.java.bot.httpclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.bot.configuration.ClientConfig;
import edu.java.bot.dto.scrapper.AddLinkRequest;
import edu.java.bot.dto.scrapper.LinkResponse;
import edu.java.bot.dto.scrapper.ListLinksResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import reactor.util.retry.Retry;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WireMockTest(httpPort = 8080)
public class ScrapperClientTest {
    private final String baseUrl = "http://localhost:8080";
    ClientConfig config = new ClientConfig(Retry.backoff(1, Duration.ZERO));
    private final ScrapperClient scrapperClient = config.scrapperClient(baseUrl);

    @Test
    void shouldReturnListLinksResponse(){
        stubFor(get(format("/%s", "links"))
                .withHeader("Tg-Chat-Id", equalToJson("5"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(jsonToString("src/test/resources/listAllLinks.json"))));

        ListLinksResponse response = scrapperClient.getAllLinks(5L);
        LinkResponse linkResponse = response.links().getFirst();

        assertEquals(linkResponse.url().toString(), "link");
    }

    @Test
    @SneakyThrows
    void shouldReturnLinkResponse(){
        AddLinkRequest request = new AddLinkRequest(URI.create("link"));
        ObjectMapper objectMapper = new ObjectMapper();
        stubFor(post(format("/%s", "links"))
                .withRequestBody(equalToJson(objectMapper.writeValueAsString(request)))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(jsonToString("src/test/resources/linkResponse.json"))));

        LinkResponse response = scrapperClient.addLink(0L, request);

        assertEquals(response.url().toString(), "link");
    }

    @SneakyThrows
    public String jsonToString(String filePath) {
        return Files.readString(Paths.get(filePath));
    }
}
