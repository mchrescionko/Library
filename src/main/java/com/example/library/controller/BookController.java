package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.model.User;
import com.example.library.service.BookService;
import com.example.library.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class BookController {
    private BookService bookService;
    private UserService userService;

    @GetMapping("/book")
    String book(String id, Model model, String message) throws Exception {
        Book book = bookService.SearchByID(id);
        model.addAttribute("book", book);
        model.addAttribute("owners", bookService.searchOwnersWithoutLoggedUser(book, userService.loggedUser().getId()));
        if (message != null) {
            model.addAttribute("message", message);
            return "bookWithMessage";
        }
        return "book";
    }
}
