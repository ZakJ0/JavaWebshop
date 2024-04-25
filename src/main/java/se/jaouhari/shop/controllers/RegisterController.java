package se.jaouhari.shop.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.jaouhari.shop.entities.User;
import se.jaouhari.shop.services.UserService;

@Controller
public class RegisterController {

    @Autowired
    UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("customer", new User());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(@RequestParam String name,
                                      @RequestParam String lastname,
                                      @RequestParam String email,
                                      @RequestParam String adress,
                                      @RequestParam String username,
                                      @RequestParam String password,
                                      Model model) {
        String addPerson= userService.saveCustomer(name,lastname,email,adress,username,password);
        model.addAttribute("addPerson",addPerson);

        return "register";
    }
}

