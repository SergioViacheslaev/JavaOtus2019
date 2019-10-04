package ru.otus.cachehw.api.services;

import ru.otus.cachehw.api.model.User;
import java.util.Optional;

public interface DBServiceUser {

  long saveUser(User user);

  Optional<User> getUser(long id);


}
