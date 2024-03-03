package edu.java.exception;

import java.net.URI;

public class LinkAlreadyAddedException extends Exception {
    public LinkAlreadyAddedException(URI link) {
        super(String.format("Link %s already added", link.toString()));
    }
}
