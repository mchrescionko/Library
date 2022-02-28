package com.example.library.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "exchanges")
@Data
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
    private boolean firstStep;
    private boolean secondStep;
    private boolean thirdStep;

    public Exchange() {
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

    public void setFirstStep(boolean firstStep) {
        this.firstStep = firstStep;
    }

    public void setSecondStep(boolean secondStep) {
        this.secondStep = secondStep;
    }

    public void setThirdStep(boolean thirdStep) {
        this.thirdStep = thirdStep;
    }
}
