package edu.java.bot.command.replyCommand;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.dto.scrapper.RemoveLinkRequest;
import edu.java.bot.httpclient.ScrapperClient;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UntrackCommand extends AbstractReplyCommand {
    private final ScrapperClient scrapperClient;

    @Override
    public String getCommand() {
        return "/untrack";
    }

    @Override
    public String getDescription() {
        return "Stop tracking a link";
    }

    @Override
    public String replyText() {
        return "Enter the link to remove:";
    }

    @Override
    public SendMessage handleReply(Update update) {
        String link = update.message().text();
        try {
            RemoveLinkRequest request = new RemoveLinkRequest(URI.create(link));
            scrapperClient.removeLink(update.message().chat().id(), request);
        } catch (Exception ex) {
            log.error("error when deleting link", ex);
            return new SendMessage(update.message().chat().id(), "An error occurred during deletion");
        }
        return new SendMessage(update.message().chat().id(), "Link successfully removed");
    }
}
