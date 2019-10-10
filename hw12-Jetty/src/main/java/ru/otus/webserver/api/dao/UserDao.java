package ru.otus.webserver.api.dao;

import ru.otus.webserver.api.model.User;
import ru.otus.webserver.api.sessionmanager.SessionManager;

import java.util.Optional;

public interface UserDao {
  Optional<User> findById(long id);

  long saveUser(User user);

  Optional<User> loadUser(long id);

  SessionManager getSessionManager();
}
