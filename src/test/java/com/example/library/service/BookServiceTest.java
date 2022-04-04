package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.model.User;
import com.example.library.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;

class BookServiceTest {

    @Test
    public void test1() {

        UserRepository userRepositoryMock = Mockito.mock(UserRepository.class);
        BookService bookService = new BookService(userRepositoryMock, null);

        User otherUSer = User.builder()
                .id(2)
                .build();
        User userLogged = User.builder()
                .id(1)
                .build();

        List<User> users = new ArrayList<>();
        users.add(otherUSer);
        users.add(userLogged);

        Book book = Book.builder()
                .id("bookId")
                .build();
        Integer userLoggedId = 1;

        Mockito.when(userRepositoryMock.findByBooks(book)).thenReturn(users);
        Mockito.when(userRepositoryMock.getById(userLoggedId)).thenReturn(userLogged);

        List<User> userList = bookService.searchOwnersWithoutLoggedUser(book, userLoggedId);

        Assertions.assertEquals(1, userList.size());
        Assertions.assertEquals(otherUSer, userList.get(0));
    }
}