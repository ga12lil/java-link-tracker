package edu.java.linkparser;

import edu.java.linkparser.response.ParseResponse;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public final class LinkParser {
    private final ParseChain parseChain;

    public ParseResponse parse(URI link) {
        return parseChain.parse(link);
    }
}
