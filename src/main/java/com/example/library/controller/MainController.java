package com.example.library.controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
    @RequestMapping (path = "/", method = RequestMethod.GET)
    String getHomePage (){
        System.out.println("page");
        return "index.html";
    }
    @GetMapping ("/myLibrary")
    String getMyLibrary (){
        System.out.println("library");
        return "library";
    }
}
