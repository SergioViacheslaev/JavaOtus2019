package ru.otus.springmvcwebapp.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.springmvcwebapp.api.services.DBServiceCachedUser;
import ru.otus.springmvcwebapp.front.FrontendService;
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

    @Autowired
    private FrontendService frontendService;

    //Список новых добавленных пользователей
    private List<User> newAddedUsers = new ArrayList<>();

    private Gson gson = new Gson();
    private JsonParser jsonParser = new JsonParser();



    public AdminPanelController(DBServiceCachedUser serviceUser) {
        this.serviceUser = serviceUser;
    }

 /*   @Autowired
    public void setFrontendService(FrontendService frontendService) {
        this.frontendService = frontendService;
    }*/

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


        JsonObject jsonObject = jsonParser.parse(message).getAsJsonObject();
        System.out.println(jsonObject);

        JsonObject messageStr = jsonObject.getAsJsonObject("messageStr");
        System.out.println(messageStr);

        String jsonString = messageStr.toString();

        User newUser = gson.fromJson(jsonString, User.class);


        serviceUser.saveUser(newUser);

//        frontendService.getUserData(3L,null);


        return jsonString;


    }


}
