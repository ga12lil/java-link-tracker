package edu.java.bot.repository;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;


@Repository
public class UserRepository {
    private final Map<Long, User> users = new HashMap<>(); //пока так

    public User getUser(Long id) {
        addUser(id);
        return users.get(id);
    }

    public void addUser(Long id) {
        users.putIfAbsent(id, new User(id));
    }
}
