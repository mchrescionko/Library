package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.model.Exchange;
import com.example.library.model.ExchangeStep;
import com.example.library.model.User;
import com.example.library.repository.ExchangeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ExchangeService {
    private ExchangeRepository exchangeRepository;
    private SearchService searchService;
    private LoginService loginService;

    public void createExchange(String receiverBookId, int receiverId) {
        Exchange exchange = Exchange.builder()
                .receiver(loginService.getUserById(receiverId))
                .receiverBook(searchService.SearchByID(receiverBookId))
                .sender(loginService.loggedUser())
                .exchangeStep(ExchangeStep.FIRST)
                .build();
        System.out.println("uda;p sie");
        exchangeRepository.save(exchange);
    }

    public List<Exchange> getFirstStepExchangesByReceiver() {
        return exchangeRepository.findByReceiver(loginService.loggedUser(), ExchangeStep.FIRST);
    }

    public List<Exchange> getSecondStepExchangesBySender() {
        return exchangeRepository.findBySender(loginService.loggedUser(), ExchangeStep.SECOND);
    }

    public void deleteExchange(Integer id) {
        exchangeRepository.deleteById(id);
    }

    public void setSecondStep(Integer exchangeId, String senderBookId) {
        //czy to dobry sposob odpakowywania
        Exchange exchange = exchangeRepository.findById(exchangeId).orElseThrow();
        //orElseThrow + w kontrolerze tryCatch lub globalna obsługa wyjątków (tutorial)
        //exception adivce - jakoś tak - obsługa globalna poprzez filtry wyłapujące wystąpienie wyjątków

        exchange.setExchangeStep(ExchangeStep.SECOND);

        exchange.setSenderBook(searchService.SearchByID(senderBookId));
        exchangeRepository.save(exchange);

    }

    public List<Book> getSenderBooks(Integer exchangeId) {
        User sender = exchangeRepository.findById(exchangeId).orElse(null).getSender();
        return sender.getBooks();
    }

    public void finalAccept(Integer exchangeId) {
        Exchange exchange = exchangeRepository.findById(exchangeId).orElse(null);

        exchange.setExchangeStep(ExchangeStep.THIRD);
        User receiver = exchange.getReceiver();
        User sender = exchange.getSender();
        receiver.deleteBookFromBookShelf(exchange.getReceiverBook());
        receiver.addBook(exchange.getSenderBook());
        sender.deleteBookFromBookShelf(exchange.getSenderBook());
        sender.addBook(exchange.getReceiverBook());
        exchangeRepository.save(exchange);
        loginService.save(sender);
        loginService.save(receiver);

    }


}
