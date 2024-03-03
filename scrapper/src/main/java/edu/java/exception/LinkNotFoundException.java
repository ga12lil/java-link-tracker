package edu.java.exception;

import java.net.URI;

public class LinkNotFoundException extends Exception{
    public LinkNotFoundException(URI link) {
        super(String.format("Link %s not found", link.toString()));
    }
}
