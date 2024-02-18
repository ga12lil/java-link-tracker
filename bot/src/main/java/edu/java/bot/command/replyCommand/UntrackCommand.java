package edu.java.bot.command.replyCommand;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
@RequiredArgsConstructor
@Slf4j
public class UntrackCommand extends AbstractReplyCommand {
    private final UserRepository repository;

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
            repository.getUser(update.message().chat().id()).untrack(link);
        }
        catch (Exception ex) {
            log.error("error when deleting link", ex);
            return new SendMessage(update.message().chat().id(), "An error occurred during deletion");
        }
        return new SendMessage(update.message().chat().id(), "Link successfully removed");
    }
}
