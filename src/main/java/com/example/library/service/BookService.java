package com.example.library.service;

import com.example.library.exceptions.NoSuchBookException;
import com.example.library.model.Book;
import com.example.library.model.User;
import com.example.library.repository.BookRepository;
import com.example.library.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class BookService {
    private UserRepository userRepository;
    private BookRepository bookRepository;
    private final String noIdMessage = "Book with this id doesn't exist";

    public List<User> searchOwnersWithoutLoggedUser(Book book, Integer userId) {
        List<User> users = userRepository.findByBooks(book);
        users.remove(userRepository.getById(userId));
        return users;
    }

    public Book SearchByID(String id) throws NoSuchBookException {
        return bookRepository.findById(id).orElseThrow(()-> new NoSuchBookException(noIdMessage));
    }

    public void save(Book book){
        bookRepository.save(book);
    }
}
