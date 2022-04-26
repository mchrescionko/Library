package com.example.library.service;

import com.example.library.model.User;
import com.example.library.repository.UserRepository;
import com.example.library.request.UserRequest;
import com.example.library.validators.EmailValidator;
import org.hibernate.validator.constraints.Email;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    @Test
    public void registerLogicShouldThrowIllegalStateException() {
        EmailValidator emailValidatorMock = Mockito.mock(EmailValidator.class);
        UserService userService = new UserService(emailValidatorMock, null, null);

        Mockito.when(emailValidatorMock.test("someEmail")).thenReturn(false);

        assertThrows(IllegalStateException.class, () -> userService.registerLogic(UserRequest.builder().build()));
    }

    @Test
    public void signUpUserShouldThrowIllegalStateException() {
        UserRepository userRepositoryMock = Mockito.mock(UserRepository.class);
        UserService userService = new UserService(null, userRepositoryMock, null);
        User user = User.builder()
                .email("someEmail")
                .build();

        Mockito.when(userRepositoryMock.findByEmail("someEmail")).thenReturn(Optional.of(User.builder().build()));

        assertThrows(IllegalStateException.class, () -> userService.signUpUser(user));
    }

    @Test
    public void passwordValidationShouldReturnFalseIfPassowrdHasAtLeastOneBigLetteOneSmallLetterAndADigit() {
        String correctPassowrd = "1Wq";
        UserService userService = new UserService(null, null, null);
        assertFalse(userService.passwordValidation(correctPassowrd));
    }

    @Test
    public void passwordValidationShouldReturnTrueIfPassowrdDoesNotContainAtLeastOneBigLetteOneSmallLetterAndADigit() {
        String correctPassowrd = "1W";
        UserService userService = new UserService(null, null, null);
        assertTrue(userService.passwordValidation(correctPassowrd));
    }
}
