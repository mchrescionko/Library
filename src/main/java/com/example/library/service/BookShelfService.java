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

    public List<Book> booksFromBookShelf() {
        return userService.loggedUser().getBooks();
    }

    public void addBookToBookShelf(String id) {
        Book book = searchService.SearchByID(id);
        userService.addBookToLoggedUser(book);
    }

    public void deleteBookFromBookShelf(String id) {
        User user = userService.loggedUser();
        Book book = searchService.SearchByID(id);
        user.deleteBookFromBookShelf(book);
        userService.save(user);
    }
}
