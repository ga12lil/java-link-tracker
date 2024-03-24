package edu.java.repository.jooq;

import edu.java.dto.domain.LinkEntity;
import edu.java.scrapper.domain.jooq.tables.Link;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JooqLinkRepository {
    private final DSLContext create;
    private final Link link = Link.LINK;

    public List<LinkEntity> findAll() {
        return create.select(link.fields())
                .from(link)
                .fetchInto(LinkEntity.class);
    }

    public LinkEntity add(String url) {
        return create.insertInto(link)
                .set(link.URL, url)
                .returning(link.fields())
                .fetchOneInto(LinkEntity.class);
    }

    public int save(LinkEntity linkEntity) {
        return create.update(link)
                .set(link.UPDATED_AT, linkEntity.updatedAt().toLocalDateTime())
                .where(link.ID.eq(linkEntity.id()))
                .execute();
    }

    public Optional<LinkEntity> find(String url) {
        return Optional.ofNullable(
                create.select(link.fields())
                        .from(link)
                        .where(link.URL.eq(url))
                        .fetchOneInto(LinkEntity.class));
    }

    public Optional<LinkEntity> findById(Long id) {
        return Optional.ofNullable(
                create.select(link.fields())
                        .from(link)
                        .where(link.ID.eq(id))
                        .fetchOneInto(LinkEntity.class));
    }


    public int remove(String url) {
        return create.delete(link)
                .where(link.URL.eq(url))
                .execute();
    }

    public int removeById(Long id) {
        return create.delete(link)
                .where(link.ID.eq(id))
                .execute();
    }

    public List<LinkEntity> findLinksUpdatedBefore(OffsetDateTime dateTime) {
        return create.select(link.fields())
                .from(link)
                .where(link.UPDATED_AT.lessOrEqual(dateTime.toLocalDateTime()))
                .fetchInto(LinkEntity.class);
    }
}
