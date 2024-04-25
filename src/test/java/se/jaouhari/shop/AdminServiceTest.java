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
import se.jaouhari.shop.services.AdminService;

import java.util.Optional;

class AdminServiceTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateUser_shouldUpdateUserRole() {
        // Arrange
        User user = new User();
        user.setId(1);
        Optional<User> optionalUser = Optional.of(user);
        Mockito.when(userRepo.findById(1)).thenReturn(optionalUser);

        // Act
        String result = adminService.updateUser(1, 1);

        // Assert
        Assertions.assertEquals("User was succesfully updated", result);
        Assertions.assertEquals(1, user.getRole());
        Mockito.verify(userRepo, Mockito.times(1)).save(user);
    }

    @Test
    void updateUser_shouldReturnUserNotFoundIfUserDoesNotExist() {
        Mockito.when(userRepo.findById(1)).thenReturn(Optional.empty());

        String result = adminService.updateUser(1, 1);

        Assertions.assertEquals("User was not found", result);
        Mockito.verify(userRepo, Mockito.never()).save(Mockito.any(User.class));
    }

    @Test
    void newAdmin_shouldCreateNewAdmin() {
        String username = "admin";
        Mockito.when(userRepo.save(Mockito.any(User.class))).thenReturn(new User());

        String result = adminService.newAdmin("John", "Doe", "admin@example.com", "123 Street", "admin", "password");

        Assertions.assertEquals(username, result);
        Mockito.verify(userRepo, Mockito.times(1)).save(Mockito.any(User.class));
    }
}

