package ru.otus.hw16dbserver.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.hw16dbserver.cache.impl.MyCache;
import ru.otus.hw16dbserver.entity.User;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

/**
 * @author Sergei Viacheslaev
 */
@Component
public class DBServiceCachedUser {
    private final MyCache<String, User> entityCache;

    private DBServiceUser dbServiceUser;

    @PostConstruct
    private void init() {
        //cache and DB
        saveUser(new User("Vasya", "Pupkin", 22));
        saveUser(new User("Tom", "Hanks", 65));
        saveUser(new User("Bill", "Gates", 51));
        saveUser(new User("Maulder", "Fox", 35));
    }

    @Autowired
    public DBServiceCachedUser(DBServiceUser serviceUser, MyCache<String, User> entityCache) {
        this.dbServiceUser = serviceUser;
        this.entityCache = entityCache;
    }


    public long saveUser(User user) {
        long id = dbServiceUser.saveUser(user);
        entityCache.put(String.valueOf(user.getId()), user);
        return id;
    }

    public Optional<User> getUser(long id) {
        //If we get user from DB, then we also save User in cache.
        return entityCache.get(String.valueOf(id)).or(() -> dbServiceUser.getUser(id).map(user -> {
            entityCache.put(String.valueOf(id), user);
            return user;
        }));
    }

    public List<User> getUsersList() {
        return dbServiceUser.getUsersList();
    }

}
