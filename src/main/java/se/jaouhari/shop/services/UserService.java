package se.jaouhari.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.jaouhari.shop.entities.User;
import se.jaouhari.shop.repositories.UserRepo;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    EmailSender emailSender;

    public final int ROLE_USER = 0;

    public String saveCustomer(String name, String lastName, String email, String address, String username, String password) {
        User newCustomer = new User(name, lastName, email, address, username, password, ROLE_USER);
        userRepo.save(newCustomer);
        emailSender.sendRegistrationConfirmationEmail(username,email);
        return username;
    }
}

