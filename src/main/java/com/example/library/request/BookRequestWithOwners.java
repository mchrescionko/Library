package com.example.library.request;

import com.example.library.model.User;
import com.example.library.model.VolumeInfo;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

@AllArgsConstructor
@RequiredArgsConstructor
public class BookRequestWithOwners {
    private String id;
    private String kind;
    private VolumeInfo volumeInfo;
    private String title;
    private String authors;
    private String thumbnail;
    private String description;
    private List<User> owners;

    public void setOwners(List<User> owners) {
        this.owners = owners;
    }
}
