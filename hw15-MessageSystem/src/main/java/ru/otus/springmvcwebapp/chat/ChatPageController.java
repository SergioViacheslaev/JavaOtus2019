package ru.otus.springmvcwebapp.chat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Sergei Viacheslaev
 */
@Controller
public class ChatPageController {

    @GetMapping("/chat")
    public String showChat() {
        return "chat";
    }
}
