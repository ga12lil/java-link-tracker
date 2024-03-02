package edu.java.bot.command;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface Command {
    SendMessage handle(Update update);

    default boolean supports(String command) {
        return getCommand().equals(command);
    }

    default BotCommand toApiCommand() {
        return new BotCommand(getCommand(), getDescription());
    }

    String getCommand();

    String getDescription();
}
