package ru.otus.springmvcwebapp.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.otus.springmvcwebapp.api.services.DBServiceCachedUser;
import ru.otus.springmvcwebapp.front.FrontendService;
import ru.otus.springmvcwebapp.repository.User;

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

    public AdminPanelController(DBServiceCachedUser serviceUser, FrontendService frontendService, SimpMessagingTemplate messageSender) {
        this.serviceUser = serviceUser;
        this.frontendService = frontendService;
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
