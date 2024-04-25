package se.jaouhari.shop.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.jaouhari.shop.entities.Product;
import se.jaouhari.shop.services.OrderService;
import se.jaouhari.shop.services.ProductService;


import java.util.List;


@Controller
public class ProductController {

    @Autowired
    OrderService orderService;

    @Autowired
    ProductService productService;

    @GetMapping("home")
    public String getHomePage() {
        return "redirect:/products.html";
    }

    @GetMapping("/products")
    public String getProduct(@RequestParam(name = "search", required = false) String search, Model model) {
        List<Product> findProducts;
        if (search != null && !search.isEmpty()) {
            //här ger den vad man söker efter
            findProducts = productService.searchForProducts(search);
        } else {
            //om man inte söker ger den allt
            findProducts = productService.getAll();
        }
        model.addAttribute("products", findProducts);

        return "searchresult";
    }

    @GetMapping("/search")
    public String getSearch(Model model) {
        List<Product> products = productService.getAll();
        model.addAttribute("products", products);
        return "searchresult";
    }

    @PostMapping("/addproduct")
    public String addItemToDb(@RequestParam String company,
                              @RequestParam String category,
                              @RequestParam String name,
                              @RequestParam int price,
                              @RequestParam String color,
                              @RequestParam String size,
                              @RequestParam int available,
                              Model model)
    {
        model.addAttribute("add", productService.addProductToDatabase(company,category,name,price,color,size,available));
        return "redirect:/admin.html";
    }

    @GetMapping("/all")
    public String getAllProducts(Model model) {
        List<Product> list = productService.getAll();
        model.addAttribute("result", list);
        return "productpage";
    }

    @GetMapping("/phones")
    public String getPhones(Model model) {
        if (!productService.findPhones().isEmpty()) {
            model.addAttribute("phonelist", productService.findPhones());
            return "phonepage";
        } else return "redirect:/products.html";

    }
    @GetMapping("/accessories")
    public String getAccessories(Model model) {
        if (!productService.findPhones().isEmpty()) {
            model.addAttribute("accessorieslist", productService.findAccesories());
            return "accesoriespage";
        } else return "redirect:/products.html";

    }
    @GetMapping("/tv")
    public String getTv(Model model) {
        if (!productService.findPhones().isEmpty()) {
            model.addAttribute("tvlist", productService.findTvs());
            return "tvpage";
        } else return "redirect:/products.html";

    }
    @GetMapping("/computers")
    public String getComputers(Model model) {
        if (!productService.findPhones().isEmpty()) {
            model.addAttribute("computerlist", productService.findComputers());
            return "computerspage";
        } else return "redirect:/products.html";
    }

}

