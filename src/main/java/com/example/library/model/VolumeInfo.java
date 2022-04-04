package com.example.library.model;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.util.List;

public class VolumeInfo {

    @Transient
    private static String DEFAULT_IMAGE = "http://i.imgur.com/J5LVHEL.jpg";

    private String title;
    private List<String> authors;
    private String publishedDate;
    private ImageLinks imageLinks;
    @Column(length = 2000)
    private String description;
    private String buyLink;

    public VolumeInfo() {
    }

    public ImageLinks getImageLinks() {
        if(imageLinks==null){
            ImageLinks imageLinks = new ImageLinks();
            imageLinks.setThumbnail(DEFAULT_IMAGE);
            return imageLinks;
        }
        return imageLinks;
    }

    public String getThumbnail(){
        if(imageLinks==null){
            ImageLinks imageLinks = new ImageLinks();
            imageLinks.setThumbnail(DEFAULT_IMAGE);
            return imageLinks.getThumbnail();
        }
        return imageLinks.getThumbnail();
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public String getBuyLink() {
        return buyLink;
    }
}
