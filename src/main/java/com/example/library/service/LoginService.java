package com.example.library.service;

import com.example.library.repository.UserRepository;
import com.example.library.model.User;
import com.example.library.request.UserRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginService implements UserDetailsService {
    private UserRepository userRepository;
    private final static String USER_NOT_FOUND_msg = "user with email %s not found";
    private final BCryptPasswordEncoder bCryptPasswordEncoder;



    public void loginLogic(UserRequest userRequest){
        System.out.println("sprawdzam hasło.,..");
        System.out.println("logowanie udane");

        User user = userRepository.findByEmail(userRequest.getEmail()).orElse(null);


    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException(String.format(USER_NOT_FOUND_msg, email)));
    }

    public String signUpUser(User user){
        boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent();
        if(userExists){
            throw new IllegalStateException("email already taken");
        }
        String encodedPassword =  bCryptPasswordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);

        userRepository.save(user);


        return "";
    }

    public User loggedUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        return user;
    }

    public void save(User user){
        userRepository.save(user);
    }

    public User getUserById(int id){
        return userRepository.findById(id).orElse(null);
    }
}
