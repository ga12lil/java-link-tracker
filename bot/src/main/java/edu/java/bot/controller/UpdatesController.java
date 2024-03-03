package edu.java.bot.controller;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.Bot;
import edu.java.bot.dto.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/updates")
@RequiredArgsConstructor
public class UpdatesController {
    private final Bot bot;

    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    public void postUpdate(@RequestBody LinkUpdateRequest linkUpdateRequest) {
        for (var id : linkUpdateRequest.tgChatIds()) {
            bot.execute(new SendMessage(id, linkUpdateRequest.description()));
        }
    }
}
