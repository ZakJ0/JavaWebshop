package se.jaouhari.shop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import se.jaouhari.shop.entities.Product;
import se.jaouhari.shop.repositories.ProductRepository;
import se.jaouhari.shop.services.ProductService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void searchForProducts_shouldReturnMatchingProducts() {
        // Arrange
        String search = "test";
        Product product = new Product();
        List<Product> products = Collections.singletonList(product);
        Mockito.when(productRepository.findByNameContainingOrCategoryContainingOrColorContaining(search, search, search)).thenReturn(products);

        // Act
        List<Product> result = productService.searchForProducts(search);

        // Assert
        Assertions.assertEquals(products, result);
    }

    @Test
    void searchForProducts_shouldReturnEmptyListWhenNoMatchingProducts() {
        String search = "test";
        Mockito.when(productRepository.findByNameContainingOrCategoryContainingOrColorContaining(search, search, search)).thenReturn(null);

        List<Product> result = productService.searchForProducts(search);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void getAll_shouldReturnAllProducts() {
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        Product product2 = new Product();
        products.add(product1);
        products.add(product2);
        Mockito.when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getAll();

        Assertions.assertEquals(2, result.size());
    }

}

