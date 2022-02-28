package com.example.library.request;

import lombok.Data;

import javax.persistence.Entity;

@Data
public class SearchRequest {
    private String title;
    private String author;

    public SearchRequest() {
    }



}
