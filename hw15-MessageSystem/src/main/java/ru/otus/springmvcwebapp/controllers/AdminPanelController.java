package ru.otus.springmvcwebapp.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.springmvcwebapp.api.services.DBServiceCachedUser;
import ru.otus.springmvcwebapp.repository.User;

import javax.annotation.PostConstruct;
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

    private Gson gson = new Gson();

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
    public String saveUser(String message) {
        log.info("Получен запрос от фронта: {}", message);


        JsonObject jsonObject = new JsonParser().parse(message).getAsJsonObject();
        System.out.println(jsonObject);

        JsonObject messageStr = jsonObject.getAsJsonObject("messageStr");
        System.out.println(messageStr);

        String jsonString = "{\"firstName\":\"aa\",\"lastName\":\"bb\",\"age\":123}";
        User newUser = gson.fromJson(jsonString, User.class);


        serviceUser.saveUser(newUser);


        return message;



    }


}
