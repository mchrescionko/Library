package com.example.library.controller;

import com.example.library.exceptions.MyException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlingController {
    @ResponseStatus(value = HttpStatus.CONFLICT,
            reason = "Data integrity violation")  // 409
    @ExceptionHandler({MyException.class})
    public String conflict(Model model) {
        System.out.println("blablabla");
        model.addAttribute("message", "Sorry, book with such id doesn't exist!");
        return "redirect:/search.html";
    }
}
