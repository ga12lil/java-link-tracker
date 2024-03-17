package edu.java.service.linkupdater.handler;

import edu.java.dto.linkupdater.UpdateMessage;
import edu.java.linkparser.response.ParseResponse;

import java.time.OffsetDateTime;
import java.util.List;

public interface LinkHandler {
    boolean canHandle(ParseResponse parseResponse);


    OffsetDateTime getUpdates(ParseResponse parseResponse);
}
