package edu.java.service.linkupdater.handler;

import edu.java.httpclient.StackOverflowClient;
import edu.java.linkparser.response.ParseResponse;
import edu.java.linkparser.response.StackOverflowResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
@RequiredArgsConstructor
public class StackOverflowLinkHandler implements LinkHandler {
    private final StackOverflowClient stackOverflowClient;
    @Override
    public boolean canHandle(ParseResponse parseResponse) {
        return parseResponse.getClass().equals(StackOverflowResponse.class);
    }

    @Override
    public OffsetDateTime getUpdates(ParseResponse parseResponse){
        StackOverflowResponse stackOverflowResponse = (StackOverflowResponse) parseResponse;
        var response = stackOverflowClient.fetchQuestion(stackOverflowResponse.id());
        return response.updatedAt();
    }
}
