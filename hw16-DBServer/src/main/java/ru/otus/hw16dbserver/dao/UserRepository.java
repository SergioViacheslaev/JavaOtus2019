package ru.otus.hw16dbserver.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw16dbserver.entity.User;

/**
 * @author Sergei Viacheslaev
 */
public interface UserRepository extends JpaRepository<User, Long> {

}
