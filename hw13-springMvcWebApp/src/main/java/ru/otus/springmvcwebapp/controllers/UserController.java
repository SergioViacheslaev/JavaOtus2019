package ru.otus.springmvcwebapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
@RequestMapping("/users")
public class UserController {

    private DBServiceCachedUser serviceUser;

    @PostConstruct
    public void loadData() {
        serviceUser.saveUser(new User("Vasya", "Pupkin", 22));
        serviceUser.saveUser(new User("Tom", "Hanks", 65));
        serviceUser.saveUser(new User("Bill", "Gates", 51));
        serviceUser.saveUser(new User("Александр", "Пушкин", 35));
    }

    public UserController(DBServiceCachedUser serviceUser) {
        this.serviceUser = serviceUser;
    }


    @GetMapping("/list")
    public String listUsers(Model model) {
        model.addAttribute("users", serviceUser.getUsersList());
        return "users-list";
    }

    @GetMapping("/showFormForAdd")
    public String showForm(Model model) {
        User user = new User();

        model.addAttribute("user", user);

        return "user-form";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user) {

        serviceUser.saveUser(user);

        return "redirect: /users/list";
    }





/*    @RequestMapping("/showUserForm")
    public String showUserForm(Model model) {
        model.addAttribute("user", new User());
        return "user-form";
    }

    @RequestMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        serviceUser.saveUser(user);
        return "user-confirmation";
    }*/




  /*  @GetMapping("/list")
    @Transactional
    public String listCustomers(Model theModel) {

        List<User> users = serviceUser.getUsersList();

        users.forEach(System.out::println);

        theModel.addAttribute("users", users);

        return "list-users";
    }*/
}
