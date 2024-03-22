package edu.java.scheduler;

import edu.java.dto.domain.LinkEntity;
import edu.java.service.LinkService;
import edu.java.service.LinkUpdater;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;

@Component
@EnableScheduling
@Slf4j
@RequiredArgsConstructor
public class LinkUpdaterScheduler {
    private final Duration forceCheckDelay;
    private final LinkService linkService;
    private final LinkUpdater linkUpdater;

    @Scheduled(fixedDelayString = "#{@schedulerIntervalMs}")
    public void update() {
        log.info("scheduler");
        OffsetDateTime dateTime = OffsetDateTime.now().minus(forceCheckDelay);
        List<LinkEntity> linksToUpdate = linkService.findLinksUpdatedBefore(dateTime);
        for (LinkEntity linkEntity : linksToUpdate) {
            LinkEntity updatedLink = linkUpdater.update(linkEntity);
            if(!updatedLink.updatedAt().equals(linkEntity.updatedAt())){
                log.info(updatedLink.updatedAt().toString());
                log.info(linkEntity.updatedAt().toString());
                log.info("link: {} have updates!", updatedLink.url());
            }
            linkService.save(updatedLink);
        }
    }
}
