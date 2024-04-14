package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.dto.scrapper.ListLinksResponse;
import edu.java.bot.httpclient.ScrapperClient;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ListCommand implements Command {
    @Getter
    private final String command = "/list";
    @Getter
    private final String description = "Write tracking resources";
    private final ScrapperClient scrapperClient;

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();
        String message = getTrackLinks(chatId);
        return new SendMessage(chatId, message);
    }

    private String getTrackLinks(Long chatId) {
        ListLinksResponse response = scrapperClient.getAllLinks(chatId);
        SortedSet<String> trackLinks = response.links().stream()
                .map(link -> link.url().toString())
                .collect(Collectors.toCollection(TreeSet::new));
        StringBuilder message = new StringBuilder();
        if (trackLinks.isEmpty()) {
            message.append("You don't have tracking links. Use /track");
        } else {
            message.append("Tracking links:\n");
            for (String s : trackLinks) {
                message.append("- ").append(s).append("\n");
            }
        }
        return message.toString();
    }
}
