package edu.java.linkparser;

import edu.java.linkparser.processor.ParseProcessor;
import edu.java.linkparser.response.ParseResponse;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public final class ParseChain {
    private final List<ParseProcessor> processors;

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
