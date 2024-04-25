package se.jaouhari.shop.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.jaouhari.shop.entities.Product;
import se.jaouhari.shop.repositories.ProductRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public List<Product> searchForProducts(String search) {
        List<Product> products = productRepository.findByNameContainingOrCategoryContainingOrColorContaining(search, search, search);

        if (products != null) {
            return products;
        } else {
            return Collections.emptyList();
        }
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public String addProductToDatabase(String company, String category, String name, int price, String color, String size, int available) {
        Product product = new Product();

        product.setCompany(company);
        product.setCategory(category);
        product.setName(name);
        product.setPrice(price);
        product.setColor(color);
        product.setSize(size);
        product.setAvailable(available);
        List<Product> all = productRepository.findAll();
        for (Product e : all) {
            if (e.getProductId() != product.getProductId()) {
                productRepository.save(product);
                return "product has been saved";
            } else return "product already exists";
        }
        return "something went wrong";
    }

    public String removeItem(int productId) {
        List<Product> list = productRepository.findByProductId(productId);

        for (Product product : list) {
            if (productId == product.getProductId()) {
                productRepository.deleteById(productId);
                return "item was removed";
            } else return "could not match electronic with id";
        }
        return null;
    }

    public void savedb(Product el) {
        productRepository.save(el);
    }

    public void updateProduct(int id, Product product) {
        Optional<Product> electronics = productRepository.findById(id);
        if (electronics.isPresent()) {
            Product existingProduct = electronics.get();
            existingProduct.setCompany(product.getCompany());
            existingProduct.setCategory(product.getCategory());
            existingProduct.setName(product.getName());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setColor(product.getColor());
            existingProduct.setSize(product.getSize());
            existingProduct.setAvailable(product.getAvailable());

            productRepository.save(existingProduct);
        }
    }

    public void deleteProduct(int id, Product product) {
        productRepository.deleteById(id);
    }

    public void findProductById(int id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            Product foundProduct = product.get();
            System.out.println(foundProduct.toString()); // or print specific attributes as needed
        } else {
            System.out.println("Electronic with ID " + id + " not found.");
        }
    }


    public List<Product> findAccesories() {
        return productRepository.findByCategory("Accesories");
    }

    public List<Product> findComputers() {
        return productRepository.findByCategory("Computer");
    }

    public List<Product> findTvs() {
        return productRepository.findByCategory("TV");
    }

    public List<Product> findPhones() {
        return productRepository.findByCategory("Phone");
    }
}
