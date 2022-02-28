package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.model.Exchange;
import com.example.library.model.User;
import com.example.library.service.BookShelfService;
import com.example.library.service.ExchangeService;
import com.example.library.service.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class BookShelfControler {
    private BookShelfService bookShelfService;
    private ExchangeService exchangeService;
    private LoginService loginService;

    @GetMapping("/bookShelf")
    String search(Model model){

        List<Book> bookList = bookShelfService.booksFromBookShelf();
        model.addAttribute("books", bookList);
        model.addAttribute("user", bookList);

        List<Exchange> firstStepExchangeList = exchangeService.getFirstStepExchangesByReceiver();
        model.addAttribute("firstStepExchanges", firstStepExchangeList);

        List<Exchange> secondStepExchangeList = exchangeService.getSecondStepExchangesBySender();
        model.addAttribute("secondStepExchanges", secondStepExchangeList);

        return "bookShelf";
    }
    @PostMapping("/add")
    String add(String id){
        bookShelfService.addBookToBookShelf(id);
        return  "redirect:/bookShelf";
    }
    @PostMapping("/delete")
    String delete(String id){
        bookShelfService.deleteBookFromBookShelf(id);
        return  "redirect:/bookShelf";
    }

}
