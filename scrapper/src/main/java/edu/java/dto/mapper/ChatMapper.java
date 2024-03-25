package edu.java.dto.mapper;

import edu.java.dto.domain.ChatEntity;
import edu.java.dto.domain.jpa.JpaChatEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChatMapper {
    public ChatEntity toChat(JpaChatEntity jpaChatEntity){
        return new ChatEntity(jpaChatEntity.getId());
    }

    public List<ChatEntity> toChatsList(List<JpaChatEntity> jpaChatsList){
        return jpaChatsList.stream().map(this::toChat).toList();
    }
}
