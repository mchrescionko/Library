package com.example.library.service;

import com.example.library.exceptions.NoSuchBookException;
import com.example.library.model.Book;
import com.example.library.model.User;
import com.example.library.repository.BookRepository;
import com.example.library.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class BookServiceTest {

    @Test
    public void shouldReturnListOfUsersWithoutLoggedUser() {

        UserRepository userRepositoryMock = Mockito.mock(UserRepository.class);
        BookService bookService = new BookService(userRepositoryMock, null);

        User otherUser = User.builder()
                .id(2)
                .build();
        User userLogged = User.builder()
                .id(1)
                .build();

        List<User> users = new ArrayList<>();
        users.add(otherUser);
        users.add(userLogged);

        Book book = Book.builder()
                .id("bookId")
                .build();
        Integer userLoggedId = 1;

        Mockito.when(userRepositoryMock.findByBooks(book)).thenReturn(users);
        Mockito.when(userRepositoryMock.getById(userLoggedId)).thenReturn(userLogged);

        List<User> userList = bookService.searchOwnersWithoutLoggedUser(book, userLoggedId);

        Assertions.assertEquals(1, userList.size());
        Assertions.assertEquals(otherUser, userList.get(0));
    }

    @Test
    void shouldThrowNoSuchBookExceptionIfThereIsNoBookWithSuchId() {
        //given
        BookRepository bookRepositoryMock = Mockito.mock(BookRepository.class);
        BookService bookService = new BookService(null, bookRepositoryMock);

        String id = "noSuchId";

        Optional<Book> emptyOptional = Optional.empty();
        Mockito.when(bookRepositoryMock.findById(id)).thenReturn(emptyOptional);
        //when
        assertThrows(NoSuchBookException.class, () -> bookService.SearchByID(id));

        //then

    }

    @Test
    void shouldReturnBookWithSuchId() throws NoSuchBookException {
        //given
        BookRepository bookRepositoryMock = Mockito.mock(BookRepository.class);
        BookService bookService = new BookService(null, bookRepositoryMock);
        String id = "someExistingId";

        Book book = Book.builder()
                .id(id)
                .build();

        Mockito.when(bookRepositoryMock.findById(id)).thenReturn(Optional.ofNullable(book));
        //when
        assertEquals(book, bookService.SearchByID(id));

    }
}