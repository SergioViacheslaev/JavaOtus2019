package ru.otus.orm.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.orm.api.dao.UserDao;
import ru.otus.orm.api.sessionmanager.SessionManager;
import ru.otus.orm.model.User;

import java.util.Optional;

public class DbServiceUserImpl implements DBServiceUser {
    private static Logger logger = LoggerFactory.getLogger(DbServiceUserImpl.class);

    private final UserDao userDao;

    public DbServiceUserImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public long saveUser(User user) {
        SessionManager sessionManager = userDao.getSessionManager();
        sessionManager.beginSession();
        try {
            long userId = userDao.saveUser(user);
            sessionManager.commitSession();
            sessionManager.close();

            logger.info("created user: {}", userId);
            return userId;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            sessionManager.rollbackSession();
            throw new DbServiceException(e);
        }
    }


    @Override
    public Optional<User> getUser(long id) {
        userDao.getSessionManager().beginSession();
        try {
            Optional<User> userOptional = userDao.findById(id);
            userDao.getSessionManager().close();

            logger.info("user: {}", userOptional.orElse(null));
            return userOptional;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            userDao.getSessionManager().rollbackSession();
        }
        return Optional.empty();
    }

}
