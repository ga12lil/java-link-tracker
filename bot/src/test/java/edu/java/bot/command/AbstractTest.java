package edu.java.bot.command;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.BotApplication;
import edu.java.bot.repository.User;
import edu.java.bot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

    protected UserRepository getMockRepo(SortedSet<String> links){
        User user = mock(User.class);
        when(user.getTrackLinks()).thenReturn(links);
        UserRepository repository = mock(UserRepository.class);
        when(repository.getUser(any())).thenReturn(user);
        return repository;
    }
}
