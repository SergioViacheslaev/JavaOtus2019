package ru.otus.hw16frontend.controllers;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.otus.hw16frontend.repository.User;
import ru.otus.hw16frontend.services.frontendservice.FrontendService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergei Viacheslaev
 */
@Slf4j
@Controller
@RequestMapping("/users")
public class AdminPanelController {
    //temp GSON
    private Gson gson = new Gson();

    private FrontendService frontendService;

    private SimpMessagingTemplate messageSender;


    public AdminPanelController(FrontendService frontendService, SimpMessagingTemplate messageSender) {
        this.frontendService = frontendService;
        this.messageSender = messageSender;
    }

    @GetMapping("/list")
    public String listUsers() {

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

    @MessageMapping("/getUsersList")
    public void getUsersList() {
        List<User> userList = new ArrayList<>();
        userList.add(new User(1,"Vasya", "Pupkin", 22));
        userList.add(new User(2,"Tom", "Hanks", 65));
        userList.add(new User(3,"Bill", "Gates", 51));
        userList.add(new User(4,"Maulder", "Fox", 35));

//        User tmpUser = new User(1,"Foo", "Bar", 123);
        String userJson = gson.toJson(userList);

        sendFrontMessage(userJson);
    }

    //Служит для отправки ответного сообщения в WebSocket из DBService
    private void sendFrontMessage(String frontMessage) {
        messageSender.convertAndSend("/topic/DBServiceResponse", frontMessage);
    }


}
