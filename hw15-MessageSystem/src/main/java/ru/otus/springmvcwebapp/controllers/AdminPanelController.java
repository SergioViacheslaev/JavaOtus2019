package ru.otus.springmvcwebapp.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.core.AbstractDestinationResolvingMessagingTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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

    private FrontendService frontendService;

    private SimpMessagingTemplate messageSender;

    public AdminPanelController(DBServiceCachedUser serviceUser) {
        this.serviceUser = serviceUser;
    }

    @PostConstruct
    private void init() {
        //cache and DB
        serviceUser.saveUser(new User("Vasya", "Pupkin", 22));
        serviceUser.saveUser(new User("Tom", "Hanks", 65));
        serviceUser.saveUser(new User("Bill", "Gates", 51));
        serviceUser.saveUser(new User("Maulder", "Fox", 35));
    }

    @Autowired
    public void setFrontendService(FrontendService frontendService) {
        this.frontendService = frontendService;
    }

    @Autowired
    public void setMessageSender(SimpMessagingTemplate messageSender) {
        this.messageSender = messageSender;
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
    public void saveUser(String frontMessage) {
        log.info("Получено сообщение от фронта: {}", frontMessage);

        frontendService.saveUser(frontMessage, userData -> {
            log.info("DBService ответил сообщением: {}", userData);
            sendFrontMessage(userData);
        });
    }

    //Служит для отправки ответного сообщения в WebSocket из DBService
    private void sendFrontMessage(String frontMessage) {
        messageSender.convertAndSend("/topic/DBServiceResponse", frontMessage);
    }


}
