package se.jaouhari.shop.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.jaouhari.shop.services.AddOrderService;
import se.jaouhari.shop.services.ProductService;


@Controller
public class OrderController {

    @Autowired
    AddOrderService addOrderService;

    @Autowired
    ProductService productService;

    @PostMapping("/basket")
    public String addToBasket(@RequestParam int id,
                              Model model) {
        if (addOrderService.getAvailablility(id) != 0) {
            addOrderService.addToBasket(id);
            model.addAttribute("basketItems", addOrderService.getBasketItems());
            return "basket";
        } else {
            model.addAttribute("products", productService.getAll());
            return "searchresult";
        }
    }

    @GetMapping("/orderpage")
    public String goToOrderPage(Model model) {
        model.addAttribute("orderp", "");
        model.addAttribute("basketItems", addOrderService.getBasketItems());
        model.addAttribute("price", addOrderService.getTotalCost());
        return "orderconfirmationpage";
    }

    @PostMapping("/addorder")
    public String doSetOrder(@RequestParam String username, @RequestParam String password, Model model) {
        String order = addOrderService.setOrder(username, password);
        if (order.equals("Items were ordered")) {
            model.addAttribute("addorder", order);
            return "ordermade";
        } else {
            model.addAttribute("basketItems", addOrderService.getBasketItems());
            model.addAttribute("price", addOrderService.getTotalCost());
            return "orderconfirmationpage";
        }
    }


}
