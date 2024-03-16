package edu.java.exception;

public class ChatAlreadyRegisteredException extends Exception {
    public ChatAlreadyRegisteredException(Long id) {
        super(String.format("Chat with Id: %d is already registered", id));
    }
}
