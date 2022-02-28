package com.example.library.service;

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
    private SearchService searchService;
    private BookRepository bookRepository;

    public List<User> searchOwners(Book book) {
        // ?????????????????????
        return  userRepository.findByBooks(book);
    }

    public Book SearchByID(String id) {
        return bookRepository.getById(id);
    }
}
