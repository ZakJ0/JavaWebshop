package se.jaouhari.shop.services;


import org.springframework.web.context.annotation.SessionScope;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import se.jaouhari.shop.entities.User;
import se.jaouhari.shop.repositories.UserRepo;

import java.util.Optional;


@Service
@SessionScope
public class LoginService {

    @Autowired
    public UserRepo userRepo;

    public String authenticateUser(String username, String password) {
        Optional<User> user = userRepo.findByUsername(username);
        if (user.isPresent()) {
            String storedPassword = user.get().getPassword();
            if (password.equals(storedPassword))
                return "login success";
        }
        return "could not login";
    }
}
