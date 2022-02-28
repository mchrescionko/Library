package com.example.library.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.example.library.model.Book;
import com.example.library.request.SearchRequest;
import com.example.library.service.SearchService;
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


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class GoogleBooksClient {



    //restTemplate - do zapytan
    public List<Book> getBookDetails(SearchRequest searchRequest) {
        String titleSearch = searchRequest.getTitle();
        String authorSearch = searchRequest.getAuthor();
        RestTemplate restTemplate = new RestTemplate();
        String ApiKey = "AIzaSyBxchJv2O9UZsE7iAC0Bz7_VFY_WUCXghw";
        String fooResourceURL = String.format("https://www.googleapis.com/books/v1/volumes?q=%s+inauthor:%s&key=",
                titleSearch, authorSearch);
        System.out.println("foo: "+fooResourceURL);

        String fullURL = fooResourceURL + ApiKey;
        ResponseEntity<String> response = restTemplate.getForEntity(fullURL, String.class);
        SearchResult searchResult =  restTemplate.getForObject(fullURL, SearchResult.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        JsonNode root = null;
        try {
            root = mapper.readTree(response.getBody());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        List<String> rootKeys = new ArrayList<>();
        Iterator<String> iterator = root.fieldNames();
        iterator.forEachRemaining(e -> rootKeys.add(e));
        JsonNode items = root.path("items");
        System.out.println(rootKeys);

        String jsonBooks = items.toPrettyString();
        List<Book> booksList = null;
        try {
            booksList = mapper.readValue(jsonBooks, new TypeReference<List<Book>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        for (Book book : booksList) {
            book.set();
            System.out.println("ksiazka: "+book.getTitle());
            System.out.println("zdjecie: "+book.getThumbnail());
        }

        return booksList;


//        List<String> itemsKeys = new ArrayList<>();
//        Iterator<String> iterator2 = items.fieldNames();
//        iterator2.forEachRemaining(e -> itemsKeys.add(e));
//        System.out.println(itemsKeys);
//
//        JsonNode midway = items.path(0);
//        JsonNode volumeInfo = midway.path("volumeInfo");
//
//        JsonNode title = volumeInfo.path("title");
//        System.out.println("title: " + title.toPrettyString());
//
//        List<String> rootKeys4 = new ArrayList<>();
//        Iterator<String> iterator4 = midway.fieldNames();
//        iterator4.forEachRemaining(e -> rootKeys4.add(e));
//        System.out.println(rootKeys4);
//        return title.toPrettyString();

    }

}

//autor
//zapytanie po autorze do api
//przepisujemy wyniki na listę obiektow ksiazek
//pokazujemy wynik na stronie
//uzytkownik może dodać dowolną ksiazke do swojej biblioteki
//jeśli tej ksiazki nie ma w bazie to dodajemy
//przypisujemy ta kasiazke do jego konta