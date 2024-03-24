package edu.java.repository.jooq;

import edu.java.dto.domain.ChatEntity;
import edu.java.scrapper.domain.jooq.tables.Chat;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JooqChatRepository {
    private final DSLContext create;
    private final Chat chat = Chat.CHAT;

    public int add(Long id) {
        return create.insertInto(chat)
                .set(chat.ID, id)
                .execute();
    }

    public int removeById(Long id) {
        return create.delete(chat)
                .where(chat.ID.eq(id))
                .execute();
    }

    public List<ChatEntity> findAll() {
        return create.select(chat.fields())
                .from(chat)
                .fetchInto(ChatEntity.class);
    }
}
