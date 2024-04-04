package edu.java.linkparser.processor;

import edu.java.linkparser.response.ParseResponse;
import java.net.URI;

public interface ParseProcessor {
    ParseResponse parse(URI link);
}
