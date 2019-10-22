package ru.otus.springmvcwebapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping("/showLoginPage")
	public String showMyLoginPage() {
		
		return "fancy-login";
		
	}
}
