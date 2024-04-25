package se.jaouhari.shop.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.jaouhari.shop.entities.User;
import se.jaouhari.shop.repositories.UserRepo;
import se.jaouhari.shop.services.LoginService;

import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    LoginService loginService;
    @Autowired
    UserRepo repo;

    @GetMapping("/login")
    public String showLoginForm() {
        return "redirect:/login.html";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username,
                               @RequestParam String password,
                               Model model) {
        String authenticated = loginService.authenticateUser(username, password);
        if (authenticated.equals("login success")) {
            Optional<User> userOptional = repo.findByUsername(username);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                if (user.getRole() == 1) {
                    return "redirect:/admin.html";
                } else if (user.getRole() == 0) {
                    return "redirect:/products.html";
                }
            }
        }
        return "redirect:/login.html";
    }

    @PostMapping("/logout")
    public String logout() {
        return "redirect:/login.html";
    }
}

