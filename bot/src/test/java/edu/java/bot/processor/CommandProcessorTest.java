package edu.java.bot.processor;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.command.StartCommand;
import edu.java.bot.command.replyCommand.TrackCommand;
import edu.java.bot.message.processor.CommandProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CommandProcessorTest {
    private CommandProcessor commandProcessor;

    @BeforeEach
    void setup() {
        var commands = List.of(new StartCommand(null), new TrackCommand(null));
        commandProcessor = new CommandProcessor(commands);
    }

    @Test
    void whenUnknownCommand_thenReturnUnknownCommandMessage() {
        var update = new Update();
        var message = new Message();
        var chat = new Chat();
        ReflectionTestUtils.setField(update, "message", message);
        ReflectionTestUtils.setField(message, "chat", chat);
        ReflectionTestUtils.setField(message, "text", "/nichego");
        ReflectionTestUtils.setField(chat, "id", 1L);

        var processResponse = commandProcessor.tryProcess(update);

        assertThat(processResponse.get().getParameters().get("text")).isEqualTo("Unknown command!");
    }
}