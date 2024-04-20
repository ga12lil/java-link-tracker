package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ListCommandTest extends AbstractTest {
    private final ListCommand listCommand = new ListCommand(client);

    @Test
    public void handleEmptyTrackList() {
        SortedSet<String> links = new TreeSet<>();
        getMockScrapperClient(links);
        Update update = getMockUpdate();

        assertEquals("You don't have tracking links. Use /track"
                , listCommand.handle(update).getParameters().get("text"));
    }

    @Test
    public void handleNotEmptyTrackList() {
        SortedSet<String> links = new TreeSet<>();
        links.add("Track1");
        links.add("Track2");
        getMockScrapperClient(links);
        Update update = getMockUpdate();

        assertEquals("Tracking links:\n- Track1\n- Track2\n"
                , listCommand.handle(update).getParameters().get("text"));
    }
}
