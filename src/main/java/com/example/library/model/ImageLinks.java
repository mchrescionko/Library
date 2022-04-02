package com.example.library.model;
import javax.persistence.Column;

public class ImageLinks {
    @Column(length = 5000)
    private String thumbnail;

    public ImageLinks() {
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
