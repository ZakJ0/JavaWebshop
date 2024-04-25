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
import se.jaouhari.shop.services.EmailSender;
import se.jaouhari.shop.services.UserService;

class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private EmailSender emailSender;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveCustomer_registrationCorrect() {
        // Arrange
        String name = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String address = "123 Street";
        String username = "johndoe";
        String password = "password";
        User newUser = new User(name, lastName, email, address, username, password, 0);
        Mockito.when(userRepo.save(Mockito.any(User.class))).thenReturn(newUser);
        // Act
        String result = userService.saveCustomer(name, lastName, email, address, username, password);
        // Assert
        Assertions.assertEquals("johndoe",result);
    }
    @Test
    void saveCustomer_registrationFailsNull() {
        // Arrange
        String name = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String address = "123 Street";
        String username =null;
        String password = "password";
        Mockito.when(userRepo.save(Mockito.any(User.class))).thenReturn(null);

        // Act
        String result = userService.saveCustomer(name, lastName, email, address, username, password);

        // Assert
        Assertions.assertNull(result);
    }
}



