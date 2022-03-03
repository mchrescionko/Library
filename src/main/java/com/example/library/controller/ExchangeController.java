package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.model.User;
import com.example.library.repository.BookRepository;
import com.example.library.service.ExchangeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

//zmiana url na bardziej przjerzyste
@Controller()
@AllArgsConstructor
public class ExchangeController {

    private ExchangeService exchangeService;
    private BookRepository bookRepository;

    @PostMapping("/createExchange")
    String createExchange(String receiverBookId, Integer receiverId){
        exchangeService.createExchange(receiverBookId, receiverId);
        return "redirect:book?id=" + receiverBookId;
    }

    @PostMapping("/denyExchange")
    String denyRequest(Integer id){
        exchangeService.deleteExchange(id);
        return "redirect:bookShelf";
    }

    @PostMapping("/choseSenderBook")
    String choseABook(Model model, Integer exchangeId){
        List<Book> books = exchangeService.getSenderBooks(exchangeId);
        model.addAttribute("books", books);
        model.addAttribute("exchangeId", exchangeId);
        return "senderBookShelf";
    }
    @PostMapping("/chooseSenderBook")
    String choseSenderBook(Integer exchangeId, String senderBookId){
        exchangeService.setSecondStep(exchangeId,senderBookId);
        return "redirect:bookShelf";
    }
    @PostMapping("/finalAccept")
    String finalAccept(Integer exchangeId){
        exchangeService.finalAccept(exchangeId);
        return "redirect:bookShelf";
    }

}
