package ru.otus.springmvcwebapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.springmvcwebapp.api.services.DBServiceCachedUser;
import ru.otus.springmvcwebapp.repository.AddressDataSet;
import ru.otus.springmvcwebapp.repository.PhoneDataSet;
import ru.otus.springmvcwebapp.repository.User;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Sergei Viacheslaev
 */
@Controller
@RequestMapping("/api")
public class UserController {

    private DBServiceCachedUser serviceUser;

    @PostConstruct
    public void loadData() {
        serviceUser.saveUser(new User("Vasya", "Pupkin", 22));
        serviceUser.saveUser(new User("Tom", "Hanks", 65));
        serviceUser.saveUser(new User("Bill", "Gates", 51));
    }

    @Autowired
    public UserController(DBServiceCachedUser serviceUser) {
        this.serviceUser = serviceUser;
    }


    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", serviceUser.getUsersList());
        return "list-users";
    }

  /*  @GetMapping("/list")
    @Transactional
    public String listCustomers(Model theModel) {

        List<User> users = serviceUser.getUsersList();

        users.forEach(System.out::println);

        theModel.addAttribute("users", users);

        return "list-users";
    }*/
}
