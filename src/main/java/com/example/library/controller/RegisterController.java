package com.example.library.controller;

import com.example.library.request.UserRequest;
import com.example.library.service.UserService;
import com.example.library.service.exceptions.RegisterServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;

@Controller
public class RegisterController {
    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    String register(UserRequest userRequest, Model model) {
        model.addAttribute("request", userRequest);
        return "register";
    }

    @PostMapping("/register")
    String registerPost(@Valid @ModelAttribute UserRequest userRequest, BindingResult bindingResult, Model model) throws RegisterServiceException {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        try {
            userService.registerLogic(userRequest);
        } catch (IllegalStateException e) {
            model.addAttribute("messageEmail", "this email address is not available!");
            return "register";
        }
        if(userService.passwordValidation(userRequest.getPassword())){
            System.out.println("password error");
            model.addAttribute("messagePassword", "A password has to contain at least one lower case letter, one upper case lettter and a digit");
            return "register";
        }
        return "redirect:/bookshelf";
    }
}
