package edu.java.bot.message.processor;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.command.replyCommand.AbstractReplyCommand;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@Order(2)
@RequiredArgsConstructor
public class ReplyCommandProcessor implements UpdateProcessor {
    private final List<AbstractReplyCommand> replyCommands;

    @Override
    public Optional<SendMessage> tryProcess(Update update) {
        if (!isReply(update)) {
            return Optional.empty();
        }

        for (AbstractReplyCommand replyCommand : replyCommands) {
            if (replyCommand.canHandleReply(update)) {
                return Optional.of(replyCommand.handleReply(update));
            }
        }

        return Optional.empty();
    }

    private boolean isReply(Update update) {
        return update.message().replyToMessage() != null
                && update.message().replyToMessage().from().isBot();
    }
}
