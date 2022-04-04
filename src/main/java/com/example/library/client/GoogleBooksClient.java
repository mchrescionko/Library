package com.example.library.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.example.library.model.Book;
import com.example.library.request.SearchRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import java.util.List;

@Component
public class GoogleBooksClient {
    public static String apiKey = "AIzaSyBxchJv2O9UZsE7iAC0Bz7_VFY_WUCXghw";
    public static String searchFoo = "https://www.googleapis.com/books/v1/volumes?q=%s+inauthor:%s&key=";

    public List<Book> getBookDetails(SearchRequest searchRequest) {
        String titleSearch = searchRequest.getTitle() ;
        String authorSearch = searchRequest.getAuthor();

        RestTemplate restTemplate = new RestTemplate();
        String fooResourceURL = String.format(searchFoo, titleSearch, authorSearch);
        String fullURL = fooResourceURL + apiKey;

        ResponseEntity<String> response = restTemplate.getForEntity(fullURL, String.class);
//        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JsonNode root = null;
        try {
            root = mapper.readTree(response.getBody());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        assert root != null;
        JsonNode items = root.path("items");
        String jsonBooks = items.toPrettyString();
        List<Book> booksList;
        try {
            booksList = mapper.readValue(jsonBooks, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            return null;
        }
        for (Book book : booksList) {
            book.setFields();
        }
        return booksList;
    }
}