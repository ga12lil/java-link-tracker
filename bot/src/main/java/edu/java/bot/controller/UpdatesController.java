package edu.java.bot.controller;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.Bot;
import edu.java.bot.dto.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/updates")
public class UpdatesController {
    private final Bot bot;
    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    public void postUpdate(@RequestBody LinkUpdateRequest linkUpdateRequest){
        for(var id : linkUpdateRequest.tgChatIds()){
            bot.execute(new SendMessage(id, linkUpdateRequest.description()));
        }
    }
}
