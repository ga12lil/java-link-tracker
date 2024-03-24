package edu.java.linkparser.processor;

import edu.java.linkparser.response.StackOverflowResponse;
import io.micrometer.common.lang.Nullable;

import java.net.URI;

public final class StackOverflowProcessor implements ParseProcessor {
    private static final String STACKOVERFLOW_DOMAIN = "stackoverflow.com";
    private static final String QUESTIONS_SECTION = "questions";
    private static final int PATH_SEGMENTS_COUNT = 4;

    @Override
    public StackOverflowResponse parse(URI link) {
        if (!isStackOverflowDomain(link)) {
            return null;
        }

        return extractDataFromUri(link);
    }

    private boolean isStackOverflowDomain(URI link) {
        return link.getAuthority().equals(STACKOVERFLOW_DOMAIN);
    }

    @Nullable
    private StackOverflowResponse extractDataFromUri(URI link) {
        String[] pathSegments = link.getPath().split("/");
        if (pathSegments.length != PATH_SEGMENTS_COUNT || !pathSegments[1].equals(QUESTIONS_SECTION)) {
            return null;
        }
        long id = Long.parseLong(pathSegments[2]);

        return new StackOverflowResponse(id);
    }
}