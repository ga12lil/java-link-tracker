package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.httpclient.ScrapperClient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Getter
@Component
@RequiredArgsConstructor
public class StartCommand implements Command {
    private final String command = "/start";
    private final String description = "Start dialog";
    private final static String MESSAGE = "Hi, use /help to view the list of the available commands";
    private final ScrapperClient scrapperClient;

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();
        try{
            scrapperClient.registerChat(chatId);
            return new SendMessage(chatId, MESSAGE);
        } catch (WebClientResponseException.BadRequest ex){
            return new SendMessage(chatId, "You already registered");
        }

    }
}
