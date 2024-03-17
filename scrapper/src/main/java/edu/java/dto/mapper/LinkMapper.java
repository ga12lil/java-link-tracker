package edu.java.dto.mapper;

import edu.java.dto.domain.LinkEntity;
import edu.java.dto.links.LinkResponse;
import edu.java.dto.links.ListLinksResponse;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;

@Component
public class LinkMapper {

    public LinkResponse toLinkResponse(LinkEntity linkEntity){
        return new LinkResponse(linkEntity.id(), URI.create(linkEntity.url()));
    }

    public ListLinksResponse toListLinksResponse(List<LinkEntity> linkEntities) {
        return new ListLinksResponse(
                linkEntities.stream().map(this::toLinkResponse).toList(),
                linkEntities.size());
    }
}
