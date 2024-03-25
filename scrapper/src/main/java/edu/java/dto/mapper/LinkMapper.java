package edu.java.dto.mapper;

import java.net.URI;
import java.util.List;

import edu.java.dto.links.LinkResponse;
import edu.java.dto.links.ListLinksResponse;
import edu.java.dto.domain.LinkEntity;
import edu.java.dto.domain.jpa.JpaLinkEntity;
import org.springframework.stereotype.Component;

@Component
public class LinkMapper {
    public LinkResponse toLinkResponse(LinkEntity linkEntity) {
        return new LinkResponse(linkEntity.id(), URI.create(linkEntity.url()));
    }

    public ListLinksResponse toListLinksResponse(List<LinkEntity> linkEntities) {
        return new ListLinksResponse(
                linkEntities.stream().map(this::toLinkResponse).toList(),
                linkEntities.size());
    }

    public LinkEntity toLink(JpaLinkEntity jpaLinkEntity){
        return new LinkEntity(
                jpaLinkEntity.getId(),
                jpaLinkEntity.getUrl(),
                jpaLinkEntity.getUpdatedAt(),
                jpaLinkEntity.getLastCheck()
        );
    }

    public List<LinkEntity> toLinksList(List<JpaLinkEntity> jpaLinksList){
        return jpaLinksList.stream().map(this::toLink).toList();
    }

    public JpaLinkEntity toJpaLink(LinkEntity entity){
        var jpaLinkEntity = new JpaLinkEntity();
        jpaLinkEntity.setId(entity.id());
        jpaLinkEntity.setUrl(entity.url());
        jpaLinkEntity.setUpdatedAt(entity.updatedAt());
        jpaLinkEntity.setLastCheck(entity.lastCheck());
        return jpaLinkEntity;
    }
}
