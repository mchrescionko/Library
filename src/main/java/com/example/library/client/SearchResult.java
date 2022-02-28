package com.example.library.client;

import com.example.library.model.Book;

import java.util.List;

public class SearchResult {
    private int totalItems;
    private List<Book> items;

    public SearchResult() {
    }

    public int getTotalItems() {
        return totalItems;
    }

    public List<Book> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "totalItems=" + totalItems +
                ", items=" + items +
                '}';
    }
}
