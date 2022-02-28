package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.model.User;
import com.example.library.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookShelfService {
    private LoginService loginService;
    private SearchService searchService;
    private UserRepository userRepository;

    public List<Book> booksFromBookShelf (){
        return loginService.loggedUser().getBooks();
    }



    public void addBookToBookShelf(String id){
        User user = loginService.loggedUser();
        Book book = searchService.SearchByID(id);
        user.addBook(book);
        loginService.save(user);
    }

    public void deleteBookFromBookShelf(String id){
        User user = loginService.loggedUser();
        Book book = searchService.SearchByID(id);
        user.deleteBookFromBookShelf(book);
        loginService.save(user);
    }



}
