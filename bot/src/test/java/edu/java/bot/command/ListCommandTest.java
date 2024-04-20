package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ListCommandTest extends AbstractTest {

    @Test
    public void handleEmptyTrackList() {
        SortedSet<String> links = new TreeSet<>();
        ListCommand listCommand = new ListCommand(getMockScrapperClient(links));
        Update update = getMockUpdate();

        assertEquals("You don't have tracking links. Use /track"
                , listCommand.handle(update).getParameters().get("text"));
    }

    @Test
    public void handleNotEmptyTrackList() {
        SortedSet<String> links = new TreeSet<>();
        links.add("Track1");
        links.add("Track2");
        ListCommand listCommand = new ListCommand(getMockScrapperClient(links));
        Update update = getMockUpdate();

        assertEquals("Tracking links:\n- Track1\n- Track2\n"
                , listCommand.handle(update).getParameters().get("text"));
    }
}
