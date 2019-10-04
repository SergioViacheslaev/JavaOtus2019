package ru.otus.cachehw.api.services;

import ru.otus.cachehw.api.dao.UserDao;
import ru.otus.cachehw.api.model.User;
import ru.otus.cachehw.cache.impl.MyCache;

import java.util.Optional;

/**
 * @author Sergei Viacheslaev
 */
public class DBServiceCachedUser extends DbServiceUserImpl {
    //    private static final Logger logger = LoggerFactory.getLogger(DBServiceCachedUser.class);
    private final MyCache<String, User> entityCache;

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
        String userID = String.valueOf(id);

        if (entityCache.isValueCached(userID)) {

            return Optional.ofNullable(entityCache.get(userID));

        } else {
            //If we get user from DB, then we also save User in cache.
            Optional<User> optionalUser = super.getUser(id);
            if (optionalUser.isPresent()) {
                entityCache.put(String.valueOf(id), optionalUser.get());
                return optionalUser;
            }
        }
        return Optional.empty();
    }

}
