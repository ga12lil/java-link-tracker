package edu.java.service.jpa;

import edu.java.dto.domain.ChatEntity;
import edu.java.dto.domain.jpa.JpaChatEntity;
import edu.java.dto.domain.jpa.JpaLinkEntity;
import edu.java.dto.mapper.ChatMapper;
import edu.java.exception.LinkNotFoundException;
import edu.java.repository.jpa.JpaChatRepository;
import edu.java.repository.jpa.JpaLinkRepository;
import edu.java.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.List;


@RequiredArgsConstructor
public class JpaChatService implements ChatService {
    private final JpaChatRepository chatRepository;
    private final JpaLinkRepository linkRepository;
    private final ChatMapper chatMapper;

    @Override
    @Transactional
    public void register(long id) {
        JpaChatEntity chat = new JpaChatEntity();
        chat.setId(id);
        chatRepository.save(chat);
    }

    @Override
    @Transactional
    public void unregister(long id) {
        JpaChatEntity chat = new JpaChatEntity();
        chat.setId(id);
        chatRepository.delete(chat);
    }

    @Override
    public List<ChatEntity> findByLink(URI url) throws LinkNotFoundException {
        JpaLinkEntity link = linkRepository.findByUrl(url.toString()).orElseThrow(() -> new LinkNotFoundException(url));
        List<JpaChatEntity> chats = chatRepository.findByLinksId(link.getId());
        return chatMapper.toChatsList(chats.stream().toList());
    }
}
