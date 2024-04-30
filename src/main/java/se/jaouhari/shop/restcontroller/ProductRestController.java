package se.jaouhari.shop.restcontroller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import se.jaouhari.shop.entities.Product;
import se.jaouhari.shop.services.ProductService;


import java.util.List;
import java.util.Optional;

@RestController
public class ProductRestController {
    @Autowired
    ProductService productService;

    @GetMapping("/rs/allProducts")
    public List<Product> getAllProducts(){
        return productService.getAll();
    }
    @GetMapping("/rs/productId/{productId}")
    public void getProductById(@PathVariable int productId){
        productService.findProductById(productId);
    }
    @GetMapping("/rs/product")
    public Product getProduct(){
        return new Product("samsung","computer","vlad",3000,"ginger","Medium",220);
    }
    @PostMapping("/rs/productpost")
    public String postProduct(@RequestBody Product el){
        productService.savedb(el);
        return "went well";
    }

    @PutMapping("/rs/productput/{productId}")
    public String putProduct(@PathVariable(name="productId") int productId, @RequestBody Product el, BindingResult bs) {
        if(bs.hasErrors()){
            for (ObjectError error: bs.getAllErrors()) {
                System.out.println(error.toString());
            }
        }
        productService.updateProduct(productId, el);
        return "Updated";
    }
    @DeleteMapping("/rs/productput/{productId}")
    public String deleteProduct(@PathVariable(name="productId") int productId, @RequestBody Product el) {
        productService.deleteProduct(productId, el);
        return "Deleted";
    }


    @ExceptionHandler(Exception.class)
    public void handler(Exception exp){
        exp.printStackTrace();

    }
}
