package ru.otus.orm.api.dao;

import ru.otus.orm.model.User;
import ru.otus.orm.api.sessionmanager.SessionManager;

import java.util.Optional;

public interface UserDao {
  Optional<User> findById(long id);

  long saveUser(User user);

  SessionManager getSessionManager();
}
