package edu.java.service;

import edu.java.exception.ChatNotFoundException;

public interface ChatService {
    void register(long tgChatId);

    void unregister(long tgChatId) throws ChatNotFoundException;
}
