package edu.java.service;

import edu.java.dto.domain.LinkEntity;
import edu.java.exception.ChatNotFoundException;

import java.net.URI;
import java.util.Collection;

public interface ChatService {
    void register(long tgChatId);
    void unregister(long tgChatId) throws ChatNotFoundException;
}
