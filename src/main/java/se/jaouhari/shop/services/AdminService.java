package se.jaouhari.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.jaouhari.shop.entities.User;
import se.jaouhari.shop.repositories.UserRepo;

import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private UserRepo userRepo;

    public final int ROLE_ADMIN = 1;

    // Method to update user details
    public String updateUser(int id, int role) {
        Optional<User> optionalUser = userRepo.findById(id);
        if (!optionalUser.isPresent()) {
            return "User was not found";
        }
        User user = optionalUser.get();
        user.setRole(role);
        userRepo.save(user);
        return "User was succesfully updated";
    }

    public String newAdmin(String name, String lastName, String email, String adress, String username, String password) {
        User admin = new User(name, lastName, email, adress, username, password, ROLE_ADMIN);
        userRepo.save(admin);
        return username;
    }

}


