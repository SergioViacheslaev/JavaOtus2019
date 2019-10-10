package ru.otus.cachehw.api.dao;

import ru.otus.cachehw.api.model.User;
import ru.otus.cachehw.api.sessionmanager.SessionManager;

import java.util.Optional;

public interface UserDao {
  Optional<User> findById(long id);

  long saveUser(User user);

  Optional<User> loadUser(long id);

  SessionManager getSessionManager();
}
