package ru.otus.hw16frontend.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.otus.hw16frontend.repository.User;
import ru.otus.hw16frontend.services.frontendservice.FrontendService;

/**
 * @author Sergei Viacheslaev
 */
@Slf4j
@Controller
@RequestMapping("/users")
public class AdminPanelController {

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
            sendWebSocketMessage(userData);
        });
    }

    @MessageMapping("/getUsersList")
    public void getUsersList(String frontMessage) {
        log.info("Получено сообщение от фронта: {}", frontMessage);

        frontendService.getUsersList(frontMessage, usersListData -> {
            log.info("DBService ответил сообщением: {}", usersListData);
            sendWebSocketMessage(usersListData);
        });

    }

    //Служит для отправки ответного сообщения в WebSocket из DBService
    private void sendWebSocketMessage(String frontMessage) {
        messageSender.convertAndSend("/topic/DBServiceResponse", frontMessage);
    }


}
