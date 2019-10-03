package ru.otus.cachehw.api.service;

import ru.otus.cachehw.api.model.User;
import java.util.Optional;

public interface DBServiceUser {

  long saveUser(User user);

  Optional<User> getUser(long id);

  Optional<User> loadUser(long id);

}
