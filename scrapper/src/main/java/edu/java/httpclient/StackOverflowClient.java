package edu.java.httpclient;

import edu.java.dto.httpclient.StackExchangeQuestionResponse;
import edu.java.dto.httpclient.StackExchangeResponse;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

public class StackOverflowClient {
    private final StackOverflowHttpExchange stackOverflowHttpExchange;
    private final static String BASEURL = "https://api.stackexchange.com/";
    private final static String API_VERSION = "2.3";
    private final static Map<String, String> PARAMS = new HashMap<>();

    public StackOverflowClient() {
        stackOverflowHttpExchange = factory(BASEURL)
                .createClient(StackOverflowHttpExchange.class);
        putParams();
    }

    public StackOverflowClient(String baseUrl) {
        stackOverflowHttpExchange = factory(baseUrl)
                .createClient(StackOverflowHttpExchange.class);
        putParams();
    }

    public StackExchangeQuestionResponse fetchQuestion(Long id) {
        StackExchangeResponse response = stackOverflowHttpExchange.fetchQuestion(id, API_VERSION, PARAMS);
        return response.questions().getFirst();
    }

    private HttpServiceProxyFactory factory(String baseUrl) {
        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
        WebClientAdapter adapter = WebClientAdapter.create(webClient);
        return HttpServiceProxyFactory.builderFor(adapter).build();
    }

    private void putParams() {
        PARAMS.put("site", "stackoverflow.com");
    }
}
