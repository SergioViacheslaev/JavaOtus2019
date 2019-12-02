package ru.otus.hw16dbserver.services;

import org.springframework.stereotype.Service;
import ru.otus.hw16dbserver.dao.UserRepository;
import ru.otus.hw16dbserver.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * @author Sergei Viacheslaev
 */
@Service
public class DBServiceUserImpl implements DBServiceUser {

    private UserRepository userRepository;


    public DBServiceUserImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public long saveUser(User user) {
        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    @Override
    public Optional<User> getUser(long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        return optionalUser;
    }

    @Override
    public List<User> getUsersList() {
        return userRepository.findAll();
    }
}