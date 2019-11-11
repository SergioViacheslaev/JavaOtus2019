package ru.otus.springmvcwebapp.controllers;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;
import ru.otus.springmvcwebapp.api.services.DBServiceCachedUser;
import ru.otus.springmvcwebapp.hello.Message;
import ru.otus.springmvcwebapp.messagesystem.CreateUserMessage;
import ru.otus.springmvcwebapp.repository.User;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergei Viacheslaev
 */
@Slf4j
@Controller
@RequestMapping("/users")
public class AdminPanelController {

    private DBServiceCachedUser serviceUser;

    //Список новых добавленных пользователей
    private List<User> newAddedUsers = new ArrayList<>();

    private Gson gson;

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

    @MessageMapping("/createUserMessage")
    @SendTo("/topic/DBServiceResponse")
    public CreateUserMessage saveUser(CreateUserMessage message) {
        Object gsonObject = message.getMessageStr();

        log.info("Получен запрос от фронта: {}", gsonObject);

       String jsonString =  gson.toJson(gsonObject);

       log.info("JSON STRING = {}",jsonString);

//        User newUser = gson.fromJson(gsonString, User.class);


//        serviceUser.saveUser(newUser);


        return new CreateUserMessage(HtmlUtils.htmlEscape("test"));


        //return "redirect:list";
    }


}
