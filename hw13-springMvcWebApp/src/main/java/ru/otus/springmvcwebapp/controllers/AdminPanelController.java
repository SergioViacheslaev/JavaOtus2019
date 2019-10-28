package ru.otus.springmvcwebapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.springmvcwebapp.api.services.DBServiceCachedUser;
import ru.otus.springmvcwebapp.repository.User;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Sergei Viacheslaev
 */
@Controller
@RequestMapping("/users")
public class AdminPanelController {

    private DBServiceCachedUser serviceUser;

    //Начальная инициализация базы и кэша.
    @PostConstruct
    public void loadData() {
        serviceUser.saveUser(new User("Vasya", "Pupkin", 22));
        serviceUser.saveUser(new User("Tom", "Hanks", 65));
        serviceUser.saveUser(new User("Bill", "Gates", 51));
        serviceUser.saveUser(new User("Maulder", "Fox", 35));
    }

    public AdminPanelController(DBServiceCachedUser serviceUser) {
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
    public String saveUser(@ModelAttribute("user") User user, HttpServletRequest request) {
        serviceUser.saveUser(user);

        return "redirect:list";
    }

}
