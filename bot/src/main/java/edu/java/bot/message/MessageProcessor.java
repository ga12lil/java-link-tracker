package edu.java.bot.message;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.message.processor.UpdateProcessor;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;


@Getter
@Component
@AllArgsConstructor
public class MessageProcessor {
    private final List<UpdateProcessor> updateProcessors;

    public SendMessage process(Update update) {
        for (UpdateProcessor processor : updateProcessors) {
            Optional<SendMessage> response = processor.tryProcess(update);
            if (response.isPresent()) {
                return response.get();
            }
        }

        return new SendMessage(update.message().chat().id(),
                "Incorrect command. Use /help to find out the available commands");
    }
}
