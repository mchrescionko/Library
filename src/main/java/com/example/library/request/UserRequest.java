package com.example.library.request;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UserRequest {

    @NotNull
    @NotEmpty
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    public UserRequest() {
    }




}
