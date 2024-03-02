package edu.java.bot.command.replyCommand;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.command.Command;

public abstract class AbstractReplyCommand implements Command {
    public abstract String replyText();

    public abstract SendMessage handleReply(Update update);

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), replyText())
                .replyMarkup(new ForceReply());
    }

    public boolean canHandleReply(Update update) {
        return update.message().replyToMessage() != null
                && update.message().replyToMessage().text().equals(replyText());
    }
}
