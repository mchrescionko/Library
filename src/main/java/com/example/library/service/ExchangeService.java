package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.model.Exchange;
import com.example.library.model.User;
import com.example.library.repository.ExchangeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ExchangeService {
    private ExchangeRepository exchangeRepository;
    private SearchService searchService;
    private LoginService loginService;

    public void createExchange(String receiverBookId, int receiverId) {
        Book book = searchService.SearchByID(receiverBookId);
        Exchange exchange = new Exchange();
        exchange.setReceiverBook(book);
        exchange.setReceiver(loginService.getUserById(receiverId));
        exchange.setSender(loginService.loggedUser());
        exchange.setFirstStep(true);
        exchangeRepository.save(exchange);

    }

    public List<Exchange> getFirstStepExchangesByReceiver() {
        List<Exchange> exchangeListFirstStep = new ArrayList<>();
        for (Exchange exchange : exchangeRepository.findByReceiver(loginService.loggedUser())) {
            if (exchange != null) {
                if (exchange.isFirstStep()) {
                    exchangeListFirstStep.add(exchange);
                }
            }
        }
        return exchangeListFirstStep;
    }
    public List<Exchange> getSecondStepExchangesBySender() {
        List<Exchange> exchangeListSecondStep = new ArrayList<>();
        for (Exchange exchange : exchangeRepository.findBySender(loginService.loggedUser())) {
            if (exchange != null) {
                if (exchange.isSecondStep()) {
                    exchangeListSecondStep.add(exchange);
                }
            }
        }
        return exchangeListSecondStep;
    }

    public void deleteExchange(Integer id) {
        exchangeRepository.deleteById(id);
    }

    public void setSecondStep(Integer exchangeId, String senderBookId) {
        //czy to dobry sposob odpakowywania
        Exchange exchange = exchangeRepository.findById(exchangeId).orElse(null);

        exchange.setFirstStep(false);
        exchange.setSecondStep(true);

        exchange.setSenderBook(searchService.SearchByID(senderBookId));
        exchangeRepository.save(exchange);

    }

    public List<Book> booksFromOtherUserBookShelf(User owner) {
        return owner.getBooks();
    }

    public Exchange getExchangeById(Integer exchangeId) {
        return exchangeRepository.getById(exchangeId);
    }

    public void finalAccept(Integer exchangeId){
        Exchange exchange = exchangeRepository.findById(exchangeId).orElse(null);
        exchange.setSecondStep(false);
        exchange.setThirdStep(true);
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
