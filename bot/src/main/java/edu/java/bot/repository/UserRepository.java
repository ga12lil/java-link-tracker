package edu.java.bot.repository;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository {
    private final Map<Long, User> users = new HashMap<>(); //пока так

    public User getUser(Long id) {
        if (!users.containsKey(id))
            addUser(id);
        return users.get(id);

    }

    public void addUser(Long id){
        if(!users.containsKey(id)){
            User user = new User(id);
            users.put(id, user);
        }
    }
}
