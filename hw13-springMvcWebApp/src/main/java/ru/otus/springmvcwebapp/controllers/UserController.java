package ru.otus.springmvcwebapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.otus.springmvcwebapp.api.services.DBServiceCachedUser;
import ru.otus.springmvcwebapp.repository.User;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author Sergei Viacheslaev
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private DBServiceCachedUser serviceUser;

    @PostConstruct
    public void initData() {
        serviceUser.saveUser(new User("Vasya Pupkin", 22));
        serviceUser.saveUser(new User("Tom Hanks", 65));
        serviceUser.saveUser(new User("Bill Gates", 51));
    }

    @Autowired
    public UserController(DBServiceCachedUser serviceUser) {
        this.serviceUser = serviceUser;
    }

    @GetMapping("/list")
    @Transactional
    public String listCustomers(Model theModel) {

        List<User> users = serviceUser.getUsersList();

        users.forEach(System.out::println);

        theModel.addAttribute("users", users);

        return "list-users";
    }
}
