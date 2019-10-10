package ru.otus.webserver.api.services;

import ru.otus.webserver.api.model.User;

import java.util.Optional;

public interface DBServiceUser {

  long saveUser(User user);

  Optional<User> getUser(long id);


}
