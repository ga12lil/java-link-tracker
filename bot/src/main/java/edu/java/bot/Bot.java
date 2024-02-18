package edu.java.bot;

import com.pengrad.telegrambot.ExceptionHandler;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramException;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;
import edu.java.bot.command.Command;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.message.MessageProcessor;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class Bot implements AutoCloseable, UpdatesListener, ExceptionHandler {
    private final ApplicationConfig applicationConfig;
    private final MessageProcessor messageProcessor;
    private final List<Command> commands;
    private TelegramBot bot;

    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
        bot.execute(request);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void start() {
        bot = new TelegramBot(applicationConfig.telegramToken());
        bot.setUpdatesListener(this, this);
        bot.execute(buildSetCommandsRequest(commands));
        log.info("Bot started!");
    }

    @Override
    public int process(List<Update> updates) {
        updates.stream().map(messageProcessor::process).forEach(x -> bot.execute(x));
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Override
    public void onException(TelegramException e) {
        log.error("Exception!", e);
    }

    @Override
    public void close() {
        log.info("Bot shutdown!");
        bot.shutdown();
    }

    private SetMyCommands buildSetCommandsRequest(List<Command> commands) {
        BotCommand[] botCommands = commands.stream()
                .map(Command::toApiCommand)
                .toArray(BotCommand[]::new);
        return new SetMyCommands(botCommands);
    }
}
