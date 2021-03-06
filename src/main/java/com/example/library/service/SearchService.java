package com.example.library.service;

import com.example.library.client.GoogleBooksClient;
import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import com.example.library.request.SearchRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@AllArgsConstructor
@Service
public class SearchService {
    private final GoogleBooksClient googleBooksClient;
    private final BookRepository bookRepository;

    public List<Book> search(SearchRequest searchRequest) {
        List<Book> books = googleBooksClient.getBookDetails(searchRequest);
        for (Book book : books) {
            bookRepository.save(book);
        }
        return googleBooksClient.getBookDetails(searchRequest);
    }

    public Book SearchByID(String id) {
        return bookRepository.getById(id);
    }
}
