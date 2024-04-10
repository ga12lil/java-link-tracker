package edu.java.service;

import edu.java.dto.domain.ChatEntity;
import edu.java.exception.ChatNotFoundException;
import edu.java.exception.LinkNotFoundException;
import java.net.URI;
import java.util.List;

public interface ChatService {
    void register(long tgChatId);

    void unregister(long tgChatId) throws ChatNotFoundException;

    List<ChatEntity> findByLink(URI url) throws LinkNotFoundException;
}
