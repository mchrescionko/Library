package com.example.library.service;

import com.example.library.model.AppUserRole;
import com.example.library.model.Book;
import com.example.library.model.User;
import com.example.library.repository.UserRepository;
import com.example.library.request.UserRequest;
import com.example.library.service.exceptions.RegisterServiceException;
import com.example.library.validators.EmailValidator;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.regex.Pattern;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private EmailValidator emailValidator;
    private UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final static String USER_NOT_FOUND_msg = "user with email %s not found";


    public void registerLogic(UserRequest userRequest) throws RegisterServiceException {
        boolean isValidEmail = emailValidator.test(userRequest.getEmail());
        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }
        signUpUser(
                new User(userRequest.getFirstName(),
                        userRequest.getLastName(),
                        userRequest.getEmail(),
                        userRequest.getPassword(),
                        AppUserRole.USER
                )
        );
    }

    public void signUpUser(User user) {
        boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent();
        if (userExists) {
            throw new IllegalStateException("email already taken");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    public boolean passwordValidation(String password) {
        return !Pattern.matches(("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$"), password);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_msg, email)));
    }


    public User loggedUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (User) auth.getPrincipal();
    }

    public void addBookToLoggedUser(Book book){
        loggedUser().addBook(book);
        save(loggedUser());
    }

    public void save(User user){
        userRepository.save(user);
    }

    public User getUserById(int id){
        return userRepository.findById(id).orElse(null);
    }
}
