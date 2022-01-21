package com.example.library.service;

import com.example.library.request.UserRequest;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    public void registerLogic(UserRequest userRequest){
        System.out.println("sprawdzam hasło.");
        return ;
    }
}
