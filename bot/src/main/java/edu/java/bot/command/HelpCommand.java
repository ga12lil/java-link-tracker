package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HelpCommand implements Command{
    @Getter
    private final String command = "/help";
    @Getter
    private final String description = "List of available commands";
    private final List<Command> commands;

    public HelpCommand(List<Command> commands) {
        this.commands = commands;
        this.commands.add(this);
    }
    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), getCommandsDescription());
    }

    private String getCommandsDescription() {
        StringBuilder sb = new StringBuilder();
        commands.forEach(command -> {
            sb.append(command.getCommand());
            sb.append(" — ");
            sb.append(command.getDescription());
            sb.append("\n");
        });

        return sb.toString();
    }
}
