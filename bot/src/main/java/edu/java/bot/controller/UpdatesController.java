package edu.java.bot.controller;

import edu.java.bot.dto.LinkUpdateRequest;
import edu.java.bot.service.UpdatesService;
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
    private final UpdatesService updatesService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    public void postUpdate(@RequestBody LinkUpdateRequest linkUpdateRequest) {
        updatesService.postUpdate(linkUpdateRequest);
    }
}
