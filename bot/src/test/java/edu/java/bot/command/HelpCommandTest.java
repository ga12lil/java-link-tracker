package edu.java.bot.command;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HelpCommandTest extends AbstractTest {
    @Autowired
    HelpCommand helpCommand;
    private final List<String> correctResponseStrings = List.of(
            "/list — Write tracking resources",
            "/track — Start tracking",
            "/untrack — Stop tracking a link",
            "/start — Start dialog",
            "/help — List of available commands"
    );

    @Test
    void shouldReturnAllCommands() {
        Map<String, Object> params = helpCommand.handle(getMockUpdate()).getParameters();
        List<String> responseCommands = Arrays.stream(
                params.get("text").toString().split("\n"))
                .toList();
        assertTrue(responseCommands.containsAll(correctResponseStrings));
    }
}
