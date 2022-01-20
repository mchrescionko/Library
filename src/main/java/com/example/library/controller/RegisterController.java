package com.example.library.controller;

import com.example.library.request.UserRequest;
import com.example.library.service.RegisterService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;

@Controller
public class RegisterController {
    private RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @GetMapping("/register")
    String register(){
        return "register";
    }
    @PostMapping("/register")
    String registerPost(String password, String email){
        System.out.println(password);
        System.out.println(email);

        UserRequest userRequest = new UserRequest(email, password);
        registerService.registerLogic(userRequest);

        return "index";
    }
}
