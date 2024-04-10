package edu.java.bot.command;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.BotApplication;
import edu.java.bot.dto.scrapper.LinkResponse;
import edu.java.bot.dto.scrapper.ListLinksResponse;
import edu.java.bot.httpclient.ScrapperClient;
import edu.java.bot.repository.User;
import edu.java.bot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.net.URI;
import java.util.SortedSet;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {BotApplication.class})
public class AbstractTest {
    @MockBean
    TelegramBot bot;

    @Autowired
    protected UserRepository userRepository;

    protected Update getMockUpdate(){
        Update update = mock(Update.class);
        Chat chat = mock(Chat.class);
        when(chat.id()).thenReturn(5L);
        Message message = mock(Message.class);
        when(message.chat()).thenReturn(chat);
        when(update.message()).thenReturn(message);
        return update;
    }

    protected ScrapperClient getMockScrapperClient(SortedSet<String> links){
        var allLinks = links.stream().map(x->new LinkResponse(0L, URI.create(x))).toList();
        ListLinksResponse listLinksResponse = new ListLinksResponse(allLinks, 0);
        ScrapperClient client = mock(ScrapperClient.class);
        when(client.getAllLinks(any())).thenReturn(listLinksResponse);
        return client;
    }
}
