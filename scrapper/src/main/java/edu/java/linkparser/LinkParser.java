package edu.java.linkparser;

import edu.java.linkparser.processor.GitHubProcessor;
import edu.java.linkparser.processor.StackOverflowProcessor;
import edu.java.linkparser.response.ParseResponse;
import org.springframework.stereotype.Component;

import java.net.URI;

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
