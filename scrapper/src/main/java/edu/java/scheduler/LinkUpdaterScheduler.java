package edu.java.scheduler;

import edu.java.dto.bot.LinkUpdateRequest;
import edu.java.dto.domain.ChatEntity;
import edu.java.dto.domain.LinkEntity;
import edu.java.exception.LinkNotFoundException;
import edu.java.httpclient.botclient.BotClient;
import edu.java.service.ChatService;
import edu.java.service.LinkService;
import edu.java.service.LinkUpdater;
import java.net.URI;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@Slf4j
@RequiredArgsConstructor
public class LinkUpdaterScheduler {
    private final Duration forceCheckDelay;
    private final LinkService linkService;
    private final ChatService chatService;
    private final LinkUpdater linkUpdater;
    private final BotClient botClient;

    @Scheduled(fixedDelayString = "#{@schedulerIntervalMs}")
    public void update() {
        log.info("Scheduler started updating links...");
        OffsetDateTime dateTime = OffsetDateTime.now().minus(forceCheckDelay);
        List<LinkEntity> linksToUpdate = linkService.findLinksUpdatedBefore(dateTime);
        for (LinkEntity linkEntity : linksToUpdate) {
            LinkEntity updatedLink = linkUpdater.update(linkEntity);
            if (!updatedLink.updatedAt().equals(linkEntity.updatedAt())) {
                log.info(updatedLink.updatedAt().toString());
                log.info(linkEntity.updatedAt().toString());
                log.info("link: {} have updates!", updatedLink.url());
                try {
                    List<ChatEntity> chats = chatService.findByLink(URI.create(updatedLink.url()));
                    LinkUpdateRequest request = new LinkUpdateRequest(
                            updatedLink.id(),
                            updatedLink.url(),
                            String.format(
                                    "link: %s updated\n last update: %s",
                                    updatedLink.url(),
                                    updatedLink.updatedAt()),
                            chats.stream().map((chat) -> chat.id()).toList()
                    );
                    botClient.postUpdate(request);
                } catch (LinkNotFoundException ex) {
                    log.error("link not found: " + ex.getMessage());
                }
                log.info("link: {} have updates! new updatedAt: {}, old: {}",
                        updatedLink.url(), updatedLink.updatedAt(), linkEntity.updatedAt());
            }
            linkService.save(updatedLink);
        }
    }
}
