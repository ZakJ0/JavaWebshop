package se.jaouhari.shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.jaouhari.shop.entities.Order;
import se.jaouhari.shop.repositories.OrderRepository;
import se.jaouhari.shop.services.OrderService;

import java.util.List;

@Controller
public class OrderlineController {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepo;

    @GetMapping("/orders")
    public String orders(@RequestParam String username, Model model) {
        List<Order> result = orderService.getOrders(username);
        if (!result.isEmpty()) {
            model.addAttribute("userorderlist", result );
            return "orderhistory";
        } else return "redirect:/products.html";

    }

    @GetMapping("/showallorders")
    public String showAllOrders(Model model) {
        model.addAttribute("allorderlist", orderService.getAllOrders());
        return "allorderspage";
    }



    @GetMapping("/showallpendingorders")
    public String showAllPendingOrders(Model model) {
        model.addAttribute("orders", orderService.getByStatus());
        return "showallpendingpage";
    }
    @PostMapping("/updatestatus")
    public String processUpdateOrderForm(@RequestParam int productId,
                                         @RequestParam String status,
                                         Model model) {

        model.addAttribute("update", orderService.updateStatus(productId, status));
        model.addAttribute("orders", orderService.getByStatus());
        return "showallpendingpage";
    }

}
