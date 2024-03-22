package edu.java.linkparser;

import edu.java.linkparser.processor.GitHubProcessor;
import edu.java.linkparser.processor.StackOverflowProcessor;
import edu.java.linkparser.response.ParseResponse;
import java.net.URI;
import org.springframework.stereotype.Component;

@Component
public final class LinkParser {
    private final ParseChain parseChain;

    public LinkParser() {
        parseChain = new ParseChain();
        parseChain.addProcessor(new GitHubProcessor());
        parseChain.addProcessor(new StackOverflowProcessor());
    }

    public ParseResponse parse(URI link) {
        return parseChain.parse(link);
    }
}
