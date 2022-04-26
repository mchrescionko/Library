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
    private UserService userService;
    private final String doubledAskMessage = "You already asked for this book!";

    public void createExchange(String receiverBookId, int receiverId) {
        if (doesExchangeExist(receiverBookId, receiverId, userService.loggedUser().getId())) {
            throw new RuntimeException(doubledAskMessage);
        }
        Exchange exchange = Exchange.builder()
                .receiver(userService.getUserById(receiverId))
                .receiverBook(searchService.SearchByID(receiverBookId))
                .sender(userService.loggedUser())
                .exchangeStep(ExchangeStep.FIRST)
                .build();
        exchangeRepository.save(exchange);
    }

    public boolean doesExchangeExist(String bookId, Integer receiverId, Integer senderId) {
        return exchangeRepository.findExchange(senderId, receiverId, bookId).isEmpty();
    }

    public List<Exchange> getFirstStepExchangesByReceiver() {
        return exchangeRepository.findByReceiver(userService.loggedUser(), ExchangeStep.FIRST);
    }

    public List<Exchange> getSecondStepExchangesBySender() {
        return exchangeRepository.findBySender(userService.loggedUser(), ExchangeStep.SECOND);
    }

    public void deleteExchange(Integer id) {
        exchangeRepository.deleteById(id);
    }

    public Exchange setSecondStep(Integer exchangeId, String senderBookId) {
        Exchange exchange = exchangeRepository.findById(exchangeId).orElseThrow();
        exchange.setExchangeStep(ExchangeStep.SECOND);
        exchange.setSenderBook(searchService.SearchByID(senderBookId));
        exchangeRepository.save(exchange);
        return exchange;
    }

    public List<Book> getSenderBooks(Integer exchangeId) {
        User sender = exchangeRepository.findById(exchangeId).orElseThrow(null).getSender();
        return sender.getBooks();
    }

    public void finalAccept(Integer exchangeId) {
        Exchange exchange = exchangeRepository.findById(exchangeId).orElseThrow(null);
        exchange.setExchangeStep(ExchangeStep.THIRD);
        User receiver = exchange.getReceiver();
        User sender = exchange.getSender();
        receiver.deleteBookFromBookShelf(exchange.getReceiverBook());
        receiver.addBook(exchange.getSenderBook());
        sender.deleteBookFromBookShelf(exchange.getSenderBook());
        sender.addBook(exchange.getReceiverBook());
        exchangeRepository.save(exchange);
        userService.save(sender);
        userService.save(receiver);
    }
}
