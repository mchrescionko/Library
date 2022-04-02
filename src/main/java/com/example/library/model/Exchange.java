package com.example.library.model;
import lombok.*;
import javax.persistence.*;

@Builder
@Entity
@Table(name = "exchanges")
@AllArgsConstructor

public class Exchange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String message;
    @ManyToOne(fetch = FetchType.EAGER)
    private User sender;
    @ManyToOne(fetch = FetchType.EAGER)
    private User receiver;
    @ManyToOne(fetch = FetchType.EAGER)
    private Book receiverBook;
    @ManyToOne(fetch = FetchType.EAGER)
    private Book senderBook;
    @Enumerated(EnumType.STRING)
    private ExchangeStep exchangeStep;

    public Exchange() {
    }

    public Exchange(int id, User sender, User receiver, Book receiverBook, ExchangeStep exchangeStep) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.receiverBook = receiverBook;
        this.exchangeStep = exchangeStep;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public Book getReceiverBook() {
        return receiverBook;
    }

    public void setReceiverBook(Book receiverBook) {
        this.receiverBook = receiverBook;
    }

    public Book getSenderBook() {
        return senderBook;
    }

    public void setSenderBook(Book senderBook) {
        this.senderBook = senderBook;
    }

    public void setExchangeStep(ExchangeStep exchangeStep) {
        this.exchangeStep = exchangeStep;
    }

    public ExchangeStep getExchangeStep() {
        return exchangeStep;
    }
}
