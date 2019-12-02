package ru.otus.hw16dbserver.services;

import ru.otus.hw16dbserver.entity.User;

import java.util.List;
import java.util.Optional;

public interface DBServiceUser {

    long saveUser(User user);

    Optional<User> getUser(long id);

    List<User> getUsersList();


}
