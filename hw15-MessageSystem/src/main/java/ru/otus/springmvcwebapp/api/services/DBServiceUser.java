package ru.otus.springmvcwebapp.api.services;


import ru.otus.springmvcwebapp.repository.User;

import java.util.List;
import java.util.Optional;

public interface DBServiceUser {

    long saveUser(User user);

    Optional<User> getUser(long id);

    List<User> getUsersList();


}
