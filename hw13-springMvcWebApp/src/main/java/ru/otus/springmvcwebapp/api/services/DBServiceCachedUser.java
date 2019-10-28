package ru.otus.springmvcwebapp.api.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.springmvcwebapp.api.dao.UserDao;
import ru.otus.springmvcwebapp.cache.impl.MyCache;
import ru.otus.springmvcwebapp.repository.User;

import javax.annotation.PostConstruct;
import java.util.Optional;

/**
 * @author Sergei Viacheslaev
 */
@Component
public class DBServiceCachedUser extends DbServiceUserImpl {
    private final MyCache<String, User> entityCache;

    @Autowired
    public DBServiceCachedUser(UserDao userDao, MyCache<String, User> entityCache) {
        super(userDao);
        this.entityCache = entityCache;
    }

    public DBServiceCachedUser(UserDao userDao) {
        super(userDao);
        this.entityCache = new MyCache<>();
    }

    @Override
    public long saveUser(User user) {
        long id = super.saveUser(user);
        entityCache.put(String.valueOf(user.getId()), user);
        return id;
    }

    @Override
    public Optional<User> getUser(long id) {
        //If we get user from DB, then we also save User in cache.
        return entityCache.get(String.valueOf(id)).or(() -> super.getUser(id).map(user -> {
            entityCache.put(String.valueOf(id), user);
            return user;
        }));
    }

}
