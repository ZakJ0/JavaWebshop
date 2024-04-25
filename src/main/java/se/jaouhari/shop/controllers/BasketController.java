package se.jaouhari.shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.jaouhari.shop.services.AddOrderService;


@Controller
public class BasketController {

    @Autowired
    AddOrderService addOrderService;

    @GetMapping("/getbasket")
    public String getBasket(Model model) {
        model.addAttribute("basketItems", addOrderService.getBasketItems());
        return "order_form";
    }

    @PostMapping("/removeitem")
    public String removeItemInBasket(@RequestParam int input, Model model) {
        model.addAttribute("removeitem", addOrderService.removeItemFromBasket(input));
        model.addAttribute("basketItems", addOrderService.getBasketItems());
        return "order_form";
    }

    @PostMapping("/removeamount")
    public String removeItemAmountInBasket(@RequestParam int input, Model model) {
        model.addAttribute("removeitem", addOrderService.removeAmountFromBasket(input));
        model.addAttribute("basketItems", addOrderService.getBasketItems());
        return "order_form";
    }

    @PostMapping("/add")
    public String addAmount(@RequestParam int input, Model model) {
        model.addAttribute("additem", addOrderService.addAmountInBasket(input));
        model.addAttribute("basketItems", addOrderService.getBasketItems());
        return "order_form";
    }
}

