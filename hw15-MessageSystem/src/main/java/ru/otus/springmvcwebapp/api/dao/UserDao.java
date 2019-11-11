package ru.otus.springmvcwebapp.api.dao;


import ru.otus.springmvcwebapp.api.sessionmanager.SessionManager;
import ru.otus.springmvcwebapp.repository.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> findById(long id);

    long saveUser(User user);

    Optional<User> loadUser(long id);

    SessionManager getSessionManager();

    List<User> getAllUsers();

}
