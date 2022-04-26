package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.model.Exchange;
import com.example.library.model.ExchangeStep;
import com.example.library.model.User;
import com.example.library.repository.ExchangeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class ExchangeServiceTest {
    @Test
    public void doesExchangeExistsShouldReturnTrueIfExchangeExists() {
        ExchangeRepository exchangeRepositoryMock = Mockito.mock(ExchangeRepository.class);
        ExchangeService exchangeService = new ExchangeService(exchangeRepositoryMock, null, null);

        Optional<Exchange> emptyOptional = Optional.empty();
        Mockito.when(exchangeRepositoryMock.findExchange(4, 4, "bookid")).thenReturn(emptyOptional);


        assertTrue(exchangeService.doesExchangeExist("bookid", 4, 4));

    }

    @Test
    public void doesExchangeExistsShouldReturnFalseIfExchangeDoesNotExist() {
        ExchangeRepository exchangeRepositoryMock = Mockito.mock(ExchangeRepository.class);
        ExchangeService exchangeService = new ExchangeService(exchangeRepositoryMock, null, null);
        Optional<Exchange> notEmptyOptional = Optional.of(Exchange.builder().build());
        Mockito.when(exchangeRepositoryMock.findExchange(4, 4, "bookid")).thenReturn(notEmptyOptional);

        assertFalse(exchangeService.doesExchangeExist("bookid", 4, 4));
    }

    @Test
    public void getSecondStepExchangeByReceiverShouldReturnListOfExchanges() {
        //when
        ExchangeRepository exchangeRepositoryMock = Mockito.mock(ExchangeRepository.class);
        UserService userServiceMock = Mockito.mock(UserService.class);
        ExchangeService exchangeService = new ExchangeService(exchangeRepositoryMock, null, userServiceMock);

        User loggedUSer = User.builder().build();
        List<Exchange> exchangeList = new ArrayList<>();
        Mockito.when(userServiceMock.loggedUser()).thenReturn(loggedUSer);
        Mockito.when(exchangeRepositoryMock.findByReceiver(loggedUSer, ExchangeStep.FIRST)).thenReturn(exchangeList);

        Assertions.assertEquals(exchangeList, exchangeService.getFirstStepExchangesByReceiver());

    }

    @Test
    void setSecondStepShouldChangeExchangeStepToSecond() {
        ExchangeRepository exchangeRepositoryMock = Mockito.mock(ExchangeRepository.class);
        SearchService searchServiceMock = Mockito.mock(SearchService.class);
        ExchangeService exchangeService = new ExchangeService(exchangeRepositoryMock, searchServiceMock, null);

        Exchange exchange = Exchange.builder().build();
        Book book = Book.builder().build();
        Mockito.when(exchangeRepositoryMock.findById(1)).thenReturn(Optional.ofNullable(exchange));
        Mockito.when(searchServiceMock.SearchByID("1")).thenReturn(book);

        exchangeService.setSecondStep(1, "1");
        assert exchange != null;
        Assertions.assertEquals(exchange.getExchangeStep(), ExchangeStep.SECOND);

    }

    @Test
    void setSecondStepShouldSetSenderBook() {
        ExchangeRepository exchangeRepositoryMock = Mockito.mock(ExchangeRepository.class);
        SearchService searchServiceMock = Mockito.mock(SearchService.class);
        ExchangeService exchangeService = new ExchangeService(exchangeRepositoryMock, searchServiceMock, null);

        Exchange exchange = Exchange.builder().build();
        Book book = Book.builder().build();
        Mockito.when(exchangeRepositoryMock.findById(1)).thenReturn(Optional.ofNullable(exchange));
        Mockito.when(searchServiceMock.SearchByID("1")).thenReturn(book);

        exchangeService.setSecondStep(1, "1");
        Assertions.assertEquals(exchange.getSenderBook(), book);

    }

    @Test
    void finalAcceptShouldChangeStepExchangeToThird() {
        ExchangeRepository exchangeRepositoryMock = Mockito.mock(ExchangeRepository.class);
        SearchService searchServiceMock = Mockito.mock(SearchService.class);
        UserService userServiceMock = Mockito.mock(UserService.class);
        ExchangeService exchangeService = new ExchangeService(exchangeRepositoryMock, searchServiceMock, userServiceMock);


        Book receiverBook = Book.builder().build();
        List<Book> receiverBooks = new ArrayList<>();
        receiverBooks.add(receiverBook);
        Book senderBook = Book.builder().build();
        List<Book> senderBooks = new ArrayList<>();
        senderBooks.add(senderBook);
        User receiver = User.builder()
                .books(receiverBooks)
                .build();
        User sender = User.builder()
                .books(senderBooks)
                .build();
        Exchange exchange = Exchange.builder()
                .receiver(receiver)
                .sender(sender)
                .receiverBook(receiverBook)
                .senderBook(senderBook)
                .build();
        Mockito.when(exchangeRepositoryMock.findById(1)).thenReturn(Optional.ofNullable(exchange));

        exchangeService.finalAccept(1);
        Assertions.assertEquals(exchange.getExchangeStep(), ExchangeStep.THIRD);
    }

    @Test
    void finalAcceptShouldDeleteSenderBookFromSenderBookshelf() {
        ExchangeRepository exchangeRepositoryMock = Mockito.mock(ExchangeRepository.class);
        SearchService searchServiceMock = Mockito.mock(SearchService.class);
        UserService userServiceMock = Mockito.mock(UserService.class);
        ExchangeService exchangeService = new ExchangeService(exchangeRepositoryMock, searchServiceMock, userServiceMock);

        Book receiverBook = Book.builder()
                .id("receiverBookId").
                build();
        List<Book> receiverBooks = new ArrayList<>();
        receiverBooks.add(receiverBook);
        Book senderBook = Book.builder()
                .id("senderBookId")
                .build();
        List<Book> senderBooks = new ArrayList<>();
        senderBooks.add(senderBook);
        User receiver = User.builder()
                .books(receiverBooks)
                .build();
        User sender = User.builder()
                .books(senderBooks)
                .build();
        Exchange exchange = Exchange.builder()
                .receiver(receiver)
                .sender(sender)
                .receiverBook(receiverBook)
                .senderBook(senderBook)
                .build();
        Mockito.when(exchangeRepositoryMock.findById(1)).thenReturn(Optional.ofNullable(exchange));

        exchangeService.finalAccept(1);
        assertFalse(sender.getBooks().contains(senderBook));
    }

    @Test
    void finalAcceptShouldDeleteReceiverBookFromReceiverBookshelf() {
        ExchangeRepository exchangeRepositoryMock = Mockito.mock(ExchangeRepository.class);
        SearchService searchServiceMock = Mockito.mock(SearchService.class);
        UserService userServiceMock = Mockito.mock(UserService.class);
        ExchangeService exchangeService = new ExchangeService(exchangeRepositoryMock, searchServiceMock, userServiceMock);

        Book receiverBook = Book.builder()
                .id("receiverBookId").
                build();
        List<Book> receiverBooks = new ArrayList<>();
        receiverBooks.add(receiverBook);
        Book senderBook = Book.builder()
                .id("senderBookId")
                .build();
        List<Book> senderBooks = new ArrayList<>();
        senderBooks.add(senderBook);
        User receiver = User.builder()
                .books(receiverBooks)
                .build();
        User sender = User.builder()
                .books(senderBooks)
                .build();
        Exchange exchange = Exchange.builder()
                .receiver(receiver)
                .sender(sender)
                .receiverBook(receiverBook)
                .senderBook(senderBook)
                .build();
        Mockito.when(exchangeRepositoryMock.findById(1)).thenReturn(Optional.ofNullable(exchange));

        exchangeService.finalAccept(1);
        assertFalse(receiver.getBooks().contains(receiverBook));
    }

    @Test
    void finalAcceptShouldAddSenderBookToReceiverBookshelf() {
        ExchangeRepository exchangeRepositoryMock = Mockito.mock(ExchangeRepository.class);
        SearchService searchServiceMock = Mockito.mock(SearchService.class);
        UserService userServiceMock = Mockito.mock(UserService.class);
        ExchangeService exchangeService = new ExchangeService(exchangeRepositoryMock, searchServiceMock, userServiceMock);

        Book receiverBook = Book.builder()
                .id("receiverBookId").
                build();
        List<Book> receiverBooks = new ArrayList<>();
        receiverBooks.add(receiverBook);
        Book senderBook = Book.builder()
                .id("senderBookId")
                .build();
        List<Book> senderBooks = new ArrayList<>();
        senderBooks.add(senderBook);
        User receiver = User.builder()
                .books(receiverBooks)
                .build();
        User sender = User.builder()
                .books(senderBooks)
                .build();
        Exchange exchange = Exchange.builder()
                .receiver(receiver)
                .sender(sender)
                .receiverBook(receiverBook)
                .senderBook(senderBook)
                .build();
        Mockito.when(exchangeRepositoryMock.findById(1)).thenReturn(Optional.ofNullable(exchange));

        exchangeService.finalAccept(1);
        assertTrue(receiver.getBooks().contains(senderBook));
    }

    @Test
    void finalAcceptShouldAddReceiverBookToSenderBookshelf() {
        ExchangeRepository exchangeRepositoryMock = Mockito.mock(ExchangeRepository.class);
        SearchService searchServiceMock = Mockito.mock(SearchService.class);
        UserService userServiceMock = Mockito.mock(UserService.class);
        ExchangeService exchangeService = new ExchangeService(exchangeRepositoryMock, searchServiceMock, userServiceMock);

        Book receiverBook = Book.builder()
                .id("receiverBookId").
                build();
        List<Book> receiverBooks = new ArrayList<>();
        receiverBooks.add(receiverBook);
        Book senderBook = Book.builder()
                .id("senderBookId")
                .build();
        List<Book> senderBooks = new ArrayList<>();
        senderBooks.add(senderBook);
        User receiver = User.builder()
                .books(receiverBooks)
                .build();
        User sender = User.builder()
                .books(senderBooks)
                .build();
        Exchange exchange = Exchange.builder()
                .receiver(receiver)
                .sender(sender)
                .receiverBook(receiverBook)
                .senderBook(senderBook)
                .build();
        Mockito.when(exchangeRepositoryMock.findById(1)).thenReturn(Optional.ofNullable(exchange));

        exchangeService.finalAccept(1);
        assertTrue(sender.getBooks().contains(receiverBook));
    }

}