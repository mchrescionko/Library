package com.example.library.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Data
@Getter
@Setter
public class UserRequest {

    @Email
    private String email;
    @NotBlank(message = "Can not by empty")
    private String password;
    @NotBlank(message = "Can not by empty")
    private String firstName;
    @NotBlank(message = "Can not by empty")
    private String lastName;

    public UserRequest() {
    }
}
