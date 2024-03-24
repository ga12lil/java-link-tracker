package edu.java.repository.jooq;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import edu.java.dto.domain.ChatEntity;
import edu.java.dto.domain.LinkEntity;
import edu.java.dto.domain.SubscriptionEntity;
import edu.java.scrapper.domain.jooq.tables.Chat;
import edu.java.scrapper.domain.jooq.tables.Link;
import edu.java.scrapper.domain.jooq.tables.Subscription;
import org.springframework.stereotype.Component;

import static org.jooq.impl.DSL.select;

@Component
@RequiredArgsConstructor
public class JooqSubscriptionRepository {
    private final DSLContext context;
    private final Subscription subscription = Subscription.SUBSCRIPTION;
    private final Chat chat = Chat.CHAT;
    private final Link link = Link.LINK;

    public int add(Long chatId, Long linkId) {
        return context.insertInto(subscription)
                .set(subscription.CHAT_ID, chatId)
                .set(subscription.LINK_ID, linkId)
                .execute();
    }

    public List<SubscriptionEntity> findAll() {
        return context.select(subscription.fields())
                .from(subscription)
                .fetchInto(SubscriptionEntity.class);
    }

    public List<ChatEntity> findChatsByLinkId(Long linkId) {
        return context.select(chat.fields())
                .from(chat)
                .join(subscription).on(chat.ID.eq(subscription.CHAT_ID))
                .where(subscription.LINK_ID.eq(linkId))
                .fetchInto(ChatEntity.class);
    }

    public int countByLinkId(Long linkId) {
        return context.selectCount()
                .from(subscription)
                .where(subscription.LINK_ID.eq(linkId))
                .execute();
    }

    public List<LinkEntity> findLinksByChatId(Long chatId) {
        return context.select(link.fields())
                .from(link)
                .join(subscription).on(link.ID.eq(subscription.LINK_ID))
                .where(subscription.CHAT_ID.eq(chatId))
                .fetchInto(LinkEntity.class);
    }

    public int remove(Long chatId, Long linkId) {
        return context.delete(subscription)
                .where(subscription.CHAT_ID.eq(chatId)
                        .and(subscription.LINK_ID.eq(linkId)))
                .execute();
    }

    public int removeLinksWithoutSubscribers() {
        return context.delete(link)
                .where(link.ID.in(
                        select(link.ID).from(link)
                                .leftOuterJoin(subscription)
                                .on(subscription.LINK_ID.eq(link.ID))
                                .where(subscription.CHAT_ID.isNull())
                ))
                .execute();
    }
}
