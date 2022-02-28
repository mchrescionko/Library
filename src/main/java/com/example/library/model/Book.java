package com.example.library.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="books")
@Data
public class Book {

    @Id
    private String id;
    private String kind;
    @Transient
    private VolumeInfo volumeInfo;
    @Column(length = 30000)
    private String title;
    @Column(length = 30000)
    private String authors;
    @Column(length = 30000)
    private String thumbnail;
    @Column(length = 30000)
    private String description;


    public Book() {
    }

    public String getKind() {
        return kind;
    }

    public VolumeInfo getVolumeInfo() {
        return volumeInfo;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void set(){
        String authors = "";
        if(volumeInfo.getAuthors()!=null){
            System.out.println("f");
            for(int i=0;i<volumeInfo.getAuthors().size();i++){
                if(i==volumeInfo.getAuthors().size()-1){
                    authors = authors +  volumeInfo.getAuthors().get(i);
                }else{
                    authors = authors + volumeInfo.getAuthors().get(i)+", ";
                }
            }
        }
        this.authors=authors;
        this.description = volumeInfo.getDescription();
        this.title = volumeInfo.getTitle();
        this.thumbnail = volumeInfo.getThumbnail();
    }
}
