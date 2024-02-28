package edu.java.bot.command;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelpCommandTest extends AbstractTest {
    @Autowired
    HelpCommand helpCommand;
    private final String correctResponseString =
            """
            /list — Write tracking resources
            /track — Start tracking
            /untrack — Stop tracking a link
            /start — Start dialog
            /help — List of available commands
            """;

    @Test
    void shouldReturnAllCommands() {
        Map<String, Object> params = helpCommand.handle(getMockUpdate()).getParameters();
        assertEquals(params.get("text"),
                correctResponseString);
    }
}
