package ru.otus.springmvcwebapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Sergei Viacheslaev
 */
@Controller
public class HomepageController {

    @GetMapping("/")
    public String showHomePage() {
        return "home-page";
    }

}
