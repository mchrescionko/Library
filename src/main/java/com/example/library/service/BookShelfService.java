package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class BookShelfService {
    private UserService userService;
    private SearchService searchService;
    private final String bookAlreadyInBookshelfMessage = "You already have this book!";

    public List<Book> booksFromBookShelf() {
        return userService.loggedUser().getBooks();
    }

    public void addBookToBookShelf(String id) {
        User user = userService.loggedUser();
        Book book = searchService.SearchByID(id);
        if (user.doesUserHaveBook(book)) {
            throw new RuntimeException(bookAlreadyInBookshelfMessage);
        }
        user.addBook(book);
        userService.save(user);
    }

    public void deleteBookFromBookShelf(String id) {
        User user = userService.loggedUser();
        Book book = searchService.SearchByID(id);
        user.deleteBookFromBookShelf(book);
        userService.save(user);
    }
}
