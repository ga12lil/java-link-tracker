package edu.java.linkparser;

import edu.java.linkparser.processor.ParseProcessor;
import edu.java.linkparser.response.ParseResponse;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

public final class ParseChain {
    private final List<ParseProcessor> processors = new LinkedList<>();

    public void addProcessor(ParseProcessor parseProcessor) {
        processors.add(parseProcessor);
    }

    public ParseResponse parse(URI link) {
        for (ParseProcessor processor : processors) {
            ParseResponse result = processor.parse(link);
            if (result != null) {
                return result;
            }
        }

        return null;
    }
}
