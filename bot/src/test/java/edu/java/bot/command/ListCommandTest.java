package edu.java.bot.command;

import edu.java.bot.repository.User;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.repository.UserRepository;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ListCommandTest {

    @Test
    public void handleEmptyTrackList() {
        User user = mock(User.class);
        SortedSet<String> links = new TreeSet<>();
        when(user.getTrackLinks()).thenReturn(links);
        UserRepository repository = mock(UserRepository.class);
        when(repository.getUser(any())).thenReturn(user);

        Update update = mock(Update.class);
        Chat chat = mock(Chat.class);
        when(chat.id()).thenReturn(5L);
        Message message = mock(Message.class);
        when(message.chat()).thenReturn(chat);
        when(update.message()).thenReturn(message);

        ListCommand listCommand = new ListCommand(repository);

        assertEquals("You don't have tracking links. Use /track"
                , listCommand.handle(update).getParameters().get("text"));
    }

    @Test
    public void handleNotEmptyTrackList() {
        User user = mock(User.class);
        SortedSet<String> links = new TreeSet<>();
        links.add("Track1");
        links.add("Track2");
        when(user.getTrackLinks()).thenReturn(links);
        UserRepository repository = mock(UserRepository.class);
        when(repository.getUser(any())).thenReturn(user);

        Update update = mock(Update.class);
        Chat chat = mock(Chat.class);
        when(chat.id()).thenReturn(5L);
        Message message = mock(Message.class);
        when(message.chat()).thenReturn(chat);
        when(update.message()).thenReturn(message);

        ListCommand listCommand = new ListCommand(repository);

        assertEquals("Tracking links:\n- Track1\n- Track2\n"
                , listCommand.handle(update).getParameters().get("text"));
    }

}
