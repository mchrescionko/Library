package com.example.library.service;

import com.example.library.model.AppUserRole;
import com.example.library.repository.UserRepository;
import com.example.library.model.User;
import com.example.library.request.UserRequest;
import com.example.library.service.exceptions.RegisterServiceException;
import com.example.library.validators.EmailValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RegisterService {
    private final UserRepository userRepository;
    private final LoginService loginService;
    private EmailValidator emailValidator;




    public void registerLogic(UserRequest userRequest) throws RegisterServiceException {

        boolean isValidEmail = emailValidator.test(userRequest.getEmail());

        if(!isValidEmail){
            throw new IllegalStateException("email not valid");
        }


        loginService.signUpUser(
                new User(userRequest.getFirstName(),
                        userRequest.getLastName(),
                        userRequest.getEmail(),
                        userRequest.getPassword(),
                        AppUserRole.USER
                        )
        );
    }


}
