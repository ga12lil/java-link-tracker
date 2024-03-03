package edu.java.exception;

public class ChatNotFoundException extends Exception {
    public ChatNotFoundException(Long id) {
        super(String.format("Chat with Id: %d not found", id));
    }
}
