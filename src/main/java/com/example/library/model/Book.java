package com.example.library.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name="books")
@Data
@Builder
@AllArgsConstructor
public class Book {

    @Id
    private String id;
    private String kind;
    @Transient
    private VolumeInfo volumeInfo;
    @Transient
    private SaleInfo saleInfo;
    @Column(length = 30000)
    private String title;
    @Column(length = 30000)
    private String authors;
    @Column(length = 30000)
    private String thumbnail;
    @Column(length = 30000)
    private String description;
    @Column(length = 30000)
    private String buyLink;

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
        this.title = "\""+volumeInfo.getTitle()+"\"";
        this.thumbnail = volumeInfo.getThumbnail();
        this.buyLink = saleInfo.getBuyLink();
    }
}
