package ru.otus.hw16frontend.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.otus.hw16frontend.repository.User;
import ru.otus.hw16frontend.utils.SocketClientMessageSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergei Viacheslaev
 */
@Slf4j
@Controller
@RequestMapping("/users")
public class AdminPanelController {

//    private FrontendService frontendService;

    private SocketClientMessageSystem socketClient;

    private SimpMessagingTemplate messageSender;

    public AdminPanelController(SimpMessagingTemplate messageSender) {
        this.messageSender = messageSender;
    }

    @Autowired
    public void setSocketClient(SocketClientMessageSystem socketClient) {
        this.socketClient = socketClient;
    }

    @GetMapping("/list")
    public String listUsers(Model model) {
//        model.addAttribute("users", serviceUser.getUsersList());
        List<User> users = new ArrayList<>();
        users.add(new User("Alex", "Benjamin", 33));
        users.add(new User("Maulder", "Fox", 45));

        model.addAttribute("users", users);
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
        sendFrontMessage("fooobar");
        socketClient.go();

     /*   frontendService.saveUser(frontMessage, userData -> {
            log.info("DBService ответил сообщением: {}", userData);
            sendFrontMessage(userData);
        });*/
    }

    //Служит для отправки ответного сообщения в WebSocket из DBService
    private void sendFrontMessage(String frontMessage) {
        frontMessage = "{\"firstName\":\"fe\",\"lastName\":\"fefe\",\"age\":33}";
        messageSender.convertAndSend("/topic/DBServiceResponse", frontMessage);
    }


}
