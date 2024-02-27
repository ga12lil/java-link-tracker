package edu.java.bot.repository;

import java.util.SortedSet;
import java.util.TreeSet;
import lombok.Getter;


@Getter
public class User {
    private final Long chatId;
    private final SortedSet<String> trackLinks = new TreeSet<>();

    public User(Long chatId) {
        this.chatId = chatId;
    }

    public void track(String url) {
        trackLinks.add(url);
    }

    public void untrack(String url) {
        trackLinks.remove(url);
    }
}
