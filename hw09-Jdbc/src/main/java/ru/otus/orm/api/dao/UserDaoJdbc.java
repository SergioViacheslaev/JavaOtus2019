package ru.otus.orm.api.dao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.orm.DbExecutor;
import ru.otus.orm.api.sessionmanager.SessionManager;
import ru.otus.orm.api.sessionmanager.SessionManagerJdbc;
import ru.otus.orm.model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class UserDaoJdbc implements UserDao {
    private static Logger logger = LoggerFactory.getLogger(UserDaoJdbc.class);

    private final SessionManagerJdbc sessionManager;
    private final DbExecutor<User> dbExecutor;

    public UserDaoJdbc(SessionManagerJdbc sessionManager, DbExecutor<User> dbExecutor) {
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
    }


    @Override
    public Optional<User> findById(long id) {
        try {

            return dbExecutor.selectRecord(getConnection(), "select id, name, age from user where id  = ?", id, resultSet -> {
                try {
                    if (resultSet.next()) {
                        return new User(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getInt("age"));
                    }
                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                }
                return null;
            });

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }


    @Override
    public long saveUser(User user) {
        try {
            return dbExecutor.insertRecord(getConnection(), "insert into user(name,age) values (?,?)",
                    new ArrayList<String>(
                            Arrays.asList(user.getName(), String.valueOf(user.getAge()))));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }

    private Connection getConnection() {
        return sessionManager.getConnection();
    }
}
