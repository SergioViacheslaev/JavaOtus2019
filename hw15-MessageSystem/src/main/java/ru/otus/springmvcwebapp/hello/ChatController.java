package ru.otus.springmvcwebapp.hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Sergei Viacheslaev
 */
@Controller
public class ChatController {

    @GetMapping("/chat")
    public String showChat() {
        return "chat";
    }
}
