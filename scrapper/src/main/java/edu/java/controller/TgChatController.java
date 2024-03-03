package edu.java.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tg-chat/{id}")
public class TgChatController {
    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    public void registerChat(@PathVariable Long id) {

    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.OK)
    public void removeChat(@PathVariable Long id){

    }
}
