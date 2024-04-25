package se.jaouhari.shop.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.jaouhari.shop.entities.User;
import se.jaouhari.shop.repositories.UserRepo;
import se.jaouhari.shop.services.AdminService;
import se.jaouhari.shop.services.ProductService;


import java.util.List;

@Controller
public class AdminController {
    @Autowired
    ProductService productService;
    @Autowired
    UserRepo userRepo;
    @Autowired
    AdminService adminService;

    @GetMapping("/admin")
    public String formGetAdmin() {
        return "redirect:/admin.html";
    }

    @GetMapping("/users")
    public String showAllUsers(Model model) {
        List<User> users = userRepo.findAll();

        model.addAttribute("users", users);
        return "userdetails";
    }

    @GetMapping("/reg")
    public String showRegistrationAdminForm(Model model) {
        model.addAttribute("customer", new User());
        return "adminregister";
    }

    @PostMapping("/reg")
    public String processAdminRegistration(@RequestParam String name,
                                           @RequestParam String lastname,
                                           @RequestParam String email,
                                           @RequestParam String adress,
                                           @RequestParam String username,
                                           @RequestParam String password,
                                           Model model) {
        String addPerson = adminService.newAdmin(name, lastname, email, adress, username, password);
        model.addAttribute("addAdmin", addPerson);
        return "adminregister";
    }

    @GetMapping("/update")
    public String showUpdateUserForm(Model model) {
        model.addAttribute("customer", new User());
        return "userupdate";
    }
    @PostMapping("/update")
    public String processUpdateUserForm(@ModelAttribute("user") User user, Model model) {
        String updateMessage = adminService.updateUser(user.getId(), user.getRole());
        model.addAttribute("update", updateMessage);
        return "userupdate";
    }


    @PostMapping("/deleteitemfromdb")
    public String del(@RequestParam(name = "productId") int id, Model model) {
        model.addAttribute("delete", productService.removeItem(id));
        model.addAttribute("result", productService.getAll());
        return "productpage";
    }


}
