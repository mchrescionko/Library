package com.example.library.controller;

import com.example.library.request.UserRequest;
import com.example.library.service.RegisterService;
import com.example.library.service.exceptions.RegisterServiceException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class RegisterController {
    private RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @GetMapping("/register")
    String register(Model model){
        model.addAttribute("request", new UserRequest());
        return "register";
    }
    @PostMapping("/register")
    String registerPost(@ModelAttribute("request") UserRequest userRequest, Model model){


        try {
            registerService.registerLogic(userRequest);
        } catch (RegisterServiceException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "index";
        }

        return "index";
    }
}
