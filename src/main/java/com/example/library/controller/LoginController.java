package com.example.library.controller;

import com.example.library.request.UserRequest;
import com.example.library.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    private LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login")
    String login(Model model){
        model.addAttribute("request", new UserRequest());
        System.out.println("works till here");
        return "login";
    }

    @PostMapping("/login")
    String loginPost(@ModelAttribute("request") UserRequest userRequest){

//        UserRequest userRequest = new UserRequest(email, password);
        loginService.loginLogic(userRequest);

        return "index";
    }

}
