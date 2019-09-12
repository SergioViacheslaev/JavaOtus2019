package ru.otus.orm.api.service;


import ru.otus.orm.model.User;

import java.util.Optional;

public interface DBServiceUser {

  long saveUser(User user);

  Optional<User> getUser(long id);

}
