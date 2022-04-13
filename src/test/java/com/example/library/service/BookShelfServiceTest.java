package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.model.User;
import com.example.library.repository.BookRepository;
import com.example.library.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;

class BookShelfServiceTest {

    @Test
    public void shouldReturnAListOfBooksWithAddedBook() {
        UserService userServiceMock = Mockito.mock(UserService.class);

        BookShelfService bookShelfService = new BookShelfService(userServiceMock, null);

        Book book1 = Book.builder().build();
        Book book2 = Book.builder().build();
        List<Book> books = List.of(book1, book2);
        User loggedUser = User.builder()
                .books(books)
                .build();

        Mockito.when(userServiceMock.loggedUser()).thenReturn(loggedUser);

        //then
        Assertions.assertEquals(books, bookShelfService.booksFromBookShelf());
    }

    @Test
    public void shouldInvokeAddBookMethod(){
        UserService userServiceMock = Mockito.mock(UserService.class);
        SearchService searchServiceMock = Mockito.mock(SearchService.class);

        BookShelfService bookShelfService = new BookShelfService(userServiceMock, searchServiceMock);

        Book book1 = Book.builder().build();
        List<Book> books = new ArrayList<>();
        String id = "someId";
        User loggedUser = User.builder()
                .id(1)
                .books(books)
                .build();

        Mockito.when(userServiceMock.loggedUser()).thenReturn(loggedUser);
        Mockito.when(searchServiceMock.SearchByID(id)).thenReturn(book1);

        bookShelfService.addBookToBookShelf(id);
        verify(userServiceMock).addBookToLoggedUser(book1);
    }





}