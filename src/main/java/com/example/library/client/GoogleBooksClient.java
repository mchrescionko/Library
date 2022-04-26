package com.example.library.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.example.library.model.Book;
import com.example.library.request.SearchRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Objects;

@Component
public class GoogleBooksClient {
    public static String apiKey = "AIzaSyBxchJv2O9UZsE7iAC0Bz7_VFY_WUCXghw";
    public static String searchFoo = "https://www.googleapis.com/books/v1/volumes?q=%s+inauthor:%s&key=";

    public List<Book> getBookDetails(SearchRequest searchRequest) {
        ResponseEntity<String> response = getStringResponseEntity(searchRequest);
        String jsonBooks = getString(response);
        return getBookList(jsonBooks);
    }

    private List<Book> getBookList(String jsonBooks) {
        List<Book> booksList;
        ObjectMapper mapper2 = new ObjectMapper();
        try {
            booksList = mapper2.readValue(jsonBooks, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            return null;
        }
        for (Book book : booksList) {
            book.setFields();
        }
        return booksList;
    }

    private String getString(ResponseEntity<String> response) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JsonNode root = null;
        try {
            root = mapper.readTree(response.getBody());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        JsonNode items = Objects.requireNonNull(root).path("items");
        return items.toPrettyString();
    }

    private ResponseEntity<String> getStringResponseEntity(SearchRequest searchRequest) {
        String titleSearch = searchRequest.getTitle();
        String authorSearch = searchRequest.getAuthor();

        RestTemplate restTemplate = new RestTemplate();
        String fooResourceURL = String.format(searchFoo, titleSearch, authorSearch);
        String fullURL = fooResourceURL + apiKey;

        ResponseEntity<String> response = restTemplate.getForEntity(fullURL, String.class);
        return response;
    }
}