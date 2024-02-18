package edu.java.bot.repository;

import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Getter
public class User {
    private final Long chatId;
    private final List<String> trackLinks = new ArrayList<>();

    public User(Long chatId) {
        this.chatId = chatId;
    }
    public void track(String url) {
        if (!trackLinks.contains(url)) {
            trackLinks.add(url);
        }
    }
    public void untrack(String url) {
        trackLinks.remove(url);
    }
}
