package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.service.ExchangeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

@Controller()
@AllArgsConstructor
public class ExchangeController {

    private ExchangeService exchangeService;

    @PostMapping("/askForSwap")
    String createExchange(Model model, String receiverBookId, Integer receiverId) {
        try {
            exchangeService.createExchange(receiverBookId, receiverId);
        } catch (RuntimeException e) {
            model.addAttribute("message", "You already asked for this book!");
            System.out.println("message: " + e.getMessage());
            model.addAttribute("id", receiverBookId);
            return "redirect:book";
        }
        return "redirect:book?id=" + receiverBookId;
    }

    @PostMapping("/denyExchange")
    String denyRequest(Integer id) {
        exchangeService.deleteExchange(id);
        return "redirect:bookShelf";
    }

    @PostMapping("/choseSenderBook")
    String choseABook(Model model, Integer exchangeId) {
        List<Book> books = exchangeService.getSenderBooks(exchangeId);
        model.addAttribute("books", books);
        model.addAttribute("exchangeId", exchangeId);
        return "senderBookShelf";
    }

    @PostMapping("/chooseSenderBook")
    String choseSenderBook(Integer exchangeId, String senderBookId) {
        exchangeService.setSecondStep(exchangeId, senderBookId);
        return "redirect:bookShelf";
    }

    @PostMapping("/finalAccept")
    String finalAccept(Integer exchangeId) {
        exchangeService.finalAccept(exchangeId);
        return "redirect:bookShelf";
    }
}
