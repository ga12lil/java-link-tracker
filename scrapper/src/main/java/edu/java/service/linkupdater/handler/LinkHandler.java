package edu.java.service.linkupdater.handler;

import edu.java.linkparser.response.ParseResponse;
import java.time.OffsetDateTime;

public interface LinkHandler {
    boolean canHandle(ParseResponse parseResponse);

    OffsetDateTime getUpdates(ParseResponse parseResponse);
}
