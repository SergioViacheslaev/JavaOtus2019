package ru.otus.webserver.api.services;

import ru.otus.webserver.api.model.User;

import java.util.List;
import java.util.Optional;

public interface DBServiceUser {

    long saveUser(User user);

    Optional<User> getUser(long id);

    List<User> getUsersList();


}
