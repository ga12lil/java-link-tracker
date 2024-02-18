package edu.java.bot.command.replyCommand;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.UserRepository;
import edu.java.bot.utils.URLChecker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TrackCommand extends AbstractReplyCommand {
    private final UserRepository repository;

    @Override
    public String getCommand() {
        return "/track";
    }

    @Override
    public String getDescription() {
        return "Start tracking";
    }

    @Override
    public String replyText() {
        return "Send me the URL of the resource you want to track:";
    }

    @Override
    public SendMessage handleReply(Update update) {
        String link = update.message().text();
        try {
            if (URLChecker.isValid(link)) {
                repository.getUser(update.message().chat().id()).track(link);
                return new SendMessage(update.message().chat().id(),
                        "The link has been successfully added to the list of watched ones");
            } else {
                return new SendMessage(update.message().chat().id(), "Link is incorrect");
            }

        } catch (Exception ex) {
            log.error("Error adding link to watch list!", ex);
            return new SendMessage(update.message().chat().id(),
                    "An error occurred while adding the link to your watch list.");
        }


    }
}
