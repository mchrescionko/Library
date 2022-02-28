package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.model.Exchange;
import com.example.library.model.User;
import com.example.library.repository.BookRepository;
import com.example.library.repository.UserRepository;
import com.example.library.service.BookService;
import com.example.library.service.ExchangeService;
import com.example.library.service.SearchService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class ExchangeController {

    private ExchangeService exchangeService;
    private BookRepository bookRepository;

    @PostMapping("/sendRequest")
    String sendRequest(String receiverBookId, Integer receiverId){
        System.out.println("receiverBookId: "+receiverBookId+" receiverId: "+receiverId);

        exchangeService.createExchange(receiverBookId, receiverId);

        return "redirect:book?id=" + receiverBookId;
    }
    @PostMapping("/denyExchange")
    String denyRequest(Integer id){
        exchangeService.deleteExchange(id);
        return "redirect:bookShelf";
    }

    @PostMapping("/acceptFirstStep")
    String choseABook(Model model, Integer exchangeId){
        User sender = exchangeService.getExchangeById(exchangeId).getSender();
        List<Book> books = exchangeService.booksFromOtherUserBookShelf(sender);
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
