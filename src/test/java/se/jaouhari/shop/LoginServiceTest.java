package se.jaouhari.shop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import se.jaouhari.shop.entities.User;
import se.jaouhari.shop.repositories.UserRepo;
import se.jaouhari.shop.services.LoginService;

import java.util.Optional;

class LoginServiceTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private LoginService loginService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void authenticateUser_shouldReturnLoginSuccessWhenCredentialsAreCorrect() {
        // Arrange
        String username = "testUser";
        String password = "testPassword";
        User user = new User();
        user.setPassword(password);
        Mockito.when(userRepo.findByUsername(username)).thenReturn(Optional.of(user));

        // Act
        String result = loginService.authenticateUser(username, password);

        // Assert
        Assertions.assertEquals("login success", result);
    }

    @Test
    void authenticateUser_shouldReturnCouldNotLoginWhenUserNotFound() {
        // Arrange
        String username = "testUser";
        String password = "testPassword";
        Mockito.when(userRepo.findByUsername(username)).thenReturn(Optional.empty());

        // Act
        String result = loginService.authenticateUser(username, password);

        // Assert
        Assertions.assertEquals("could not login", result);
    }

    @Test
    void authenticateUser_shouldReturnCouldNotLoginWhenPasswordIsIncorrect() {
        // Arrange
        String username = "testUser";
        String password = "testPassword";
        User user = new User();
        user.setPassword("incorrectPassword");
        Mockito.when(userRepo.findByUsername(username)).thenReturn(Optional.of(user));

        // Act
        String result = loginService.authenticateUser(username, password);

        // Assert
        Assertions.assertEquals("could not login", result);
    }
}

