package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.request.UserRequest;
import com.example.library.service.BookService;
import com.example.library.service.SearchService;
import com.example.library.service.exceptions.SearchServiceException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@AllArgsConstructor
public class BookController {

    private BookService bookService;

    @GetMapping("/book")
    String book(Model model, String id) throws Exception {

        Book book = bookService.SearchByID(id);
        model.addAttribute("book", book);
        model.addAttribute("owners", bookService.searchOwners(book));


        return "book";
    }




}
