package com.example.library.request;

import com.example.library.model.User;
import com.example.library.model.VolumeInfo;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
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
