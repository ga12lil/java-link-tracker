package edu.java.service.jpa;

import edu.java.dto.domain.LinkEntity;
import edu.java.dto.domain.jpa.JpaChatEntity;
import edu.java.dto.domain.jpa.JpaLinkEntity;
import edu.java.dto.mapper.LinkMapper;
import edu.java.exception.ChatNotFoundException;
import edu.java.exception.LinkNotFoundException;
import edu.java.repository.jpa.JpaChatRepository;
import edu.java.repository.jpa.JpaLinkRepository;
import edu.java.service.LinkService;
import edu.java.service.LinkUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class JpaLinkService implements LinkService {

    private final JpaLinkRepository linkRepository;
    private final JpaChatRepository chatRepository;
    private final LinkUpdater linkUpdater;
    private final LinkMapper linkMapper;

    @Override
    @Transactional
    public LinkEntity add(long chatId, URI url) {
        JpaChatEntity chat = chatRepository.findById(chatId)
                .orElseThrow();

        JpaLinkEntity linkEntity = linkRepository.findByUrl(url.toString()).orElseGet(() -> {
            JpaLinkEntity entityToSave = new JpaLinkEntity();
            entityToSave.setUrl(url.toString());
            return linkRepository.save(entityToSave);
        });

        JpaLinkEntity updatedLink = linkMapper.toJpaLink(linkUpdater.update(linkMapper.toLink(linkEntity)));
        linkEntity.setChats(chatRepository.findByLinksId(linkEntity.getId()));
        if (!linkEntity.getChats().contains(chat)) {
            updatedLink.getChats().add(chat);
        }
        linkRepository.save(updatedLink);

        return linkMapper.toLink(updatedLink);
    }

    @Override
    @Transactional
    public LinkEntity remove(long chatId, URI url) throws LinkNotFoundException{
        JpaLinkEntity linkEntity = linkRepository
                .findByUrl(url.toString()).orElseThrow(() -> new LinkNotFoundException(url));
        JpaChatEntity chatEntity = chatRepository.findById(chatId).orElseThrow();
        linkEntity.setChats(chatRepository.findByLinksId(linkEntity.getId()));
        linkEntity.getChats().remove(chatEntity);
        linkRepository.save(linkEntity);

        return linkMapper.toLink(linkEntity);
    }

    @Override
    public List<LinkEntity> findLinksUpdatedBefore(OffsetDateTime dateTime) {
        return linkMapper.toLinksList(linkRepository.findUpdatedBefore(dateTime));
    }

    @Override
    @Transactional
    public void save(LinkEntity linkEntity) {
        JpaLinkEntity jpaLinkEntity = linkRepository
                .findById(linkEntity.id()).orElseThrow();
        jpaLinkEntity.setUpdatedAt(linkEntity.updatedAt());
        jpaLinkEntity.setLastCheck(linkEntity.lastCheck());
        linkRepository.save(jpaLinkEntity);
    }

    @Override
    @Transactional
    public List<LinkEntity> listAll(long tgChatId) {
        List<JpaLinkEntity> rawLinks = linkRepository.findByChatsId(tgChatId);
        List<LinkEntity> links = new ArrayList<>();
        for (var x : rawLinks) {
            links.add(linkMapper.toLink(x));
        }
        return links;
    }
}