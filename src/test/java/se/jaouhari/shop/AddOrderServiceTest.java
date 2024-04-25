package se.jaouhari.shop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import se.jaouhari.shop.entities.Order;
import se.jaouhari.shop.entities.Orderline;
import se.jaouhari.shop.entities.Product;
import se.jaouhari.shop.entities.User;
import se.jaouhari.shop.repositories.OrderLineRepository;
import se.jaouhari.shop.repositories.OrderRepository;
import se.jaouhari.shop.repositories.ProductRepository;
import se.jaouhari.shop.repositories.UserRepo;
import se.jaouhari.shop.services.AddOrderService;
import se.jaouhari.shop.services.EmailSender;
import java.util.ArrayList;
import java.util.List;

class AddOrderServiceTest {

    @Mock
    private OrderLineRepository orderLineRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepo userRepo;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private EmailSender emailSender;

    @InjectMocks
    private AddOrderService addOrderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addToBasket_shouldAddProductToBasket() {
        // Arrange
        Product product = new Product();
        product.setProductId(1);
        product.setPrice(10);
        List<Product> products = new ArrayList<>();
        products.add(product);
        Mockito.when(productRepository.findByProductId(1)).thenReturn(products);

        // Act
        addOrderService.addToBasket(1);

        // Assert
        Assertions.assertEquals(1, addOrderService.getBasketItems().size());
    }

    @Test
    void setOrder_shouldSetOrderAndSendConfirmationEmail() {
        // Arrange
        User user = new User();
        user.setId(1);
        List<User> users = new ArrayList<>();
        users.add(user);
        Mockito.when(userRepo.findByUsernameAndPassword("username", "password")).thenReturn(users);

        Product product = new Product();
        product.setProductId(1);
        product.setAvailable(10);
        product.setPrice(10);
        List<Product> products = new ArrayList<>();
        products.add(product);
        Mockito.when(productRepository.findByProductId(1)).thenReturn(products);

        Orderline orderline = new Orderline();
        orderline.setProductId(1);
        orderline.setAmount(1);
        List<Orderline> basketOrderLines = new ArrayList<>();
        basketOrderLines.add(orderline);
        addOrderService.basketOrderLines = basketOrderLines;

        // Act
        String result = addOrderService.setOrder("username", "password");

        // Assert
        Assertions.assertEquals("Items were ordered", result);
        Mockito.verify(orderRepository, Mockito.times(1)).save(Mockito.any(Order.class));
        Mockito.verify(orderLineRepository, Mockito.times(1)).save(Mockito.any(Orderline.class));
        Mockito.verify(emailSender, Mockito.times(1)).sendOrderConfirmationEmail("username");
    }

    @Test
    void getTotalCost_shouldCalculateTotalCostOfItemsInBasket() {
        // Arrange
        Orderline orderline1 = new Orderline();
        orderline1.setCost(10);
        Orderline orderline2 = new Orderline();
        orderline2.setCost(20);
        List<Orderline> basketOrderLines = new ArrayList<>();
        basketOrderLines.add(orderline1);
        basketOrderLines.add(orderline2);
        addOrderService.basketOrderLines = basketOrderLines;

        // Act
        int totalCost = addOrderService.getTotalCost();

        // Assert
        Assertions.assertEquals(30, totalCost);
    }

    @Test
    void getAvailablility_shouldReturnAvailabilityOfProduct() {
        // Arrange
        Product product = new Product();
        product.setProductId(1);
        product.setAvailable(5);
        List<Product> products = new ArrayList<>();
        products.add(product);
        Mockito.when(productRepository.findByProductId(1)).thenReturn(products);

        // Act
        int availability = addOrderService.getAvailablility(1);

        // Assert
        Assertions.assertEquals(5, availability);
    }

    @Test
    void removeItemFromBasket_shouldRemoveItemFromBasket() {
        // Arrange
        Orderline orderline1 = new Orderline();
        orderline1.setProductId(1);
        Orderline orderline2 = new Orderline();
        orderline2.setProductId(2);
        List<Orderline> basketOrderLines = new ArrayList<>();
        basketOrderLines.add(orderline1);
        basketOrderLines.add(orderline2);
        addOrderService.basketOrderLines = basketOrderLines;

        // Act
        addOrderService.removeItemFromBasket(1);

        // Assert
        Assertions.assertEquals(1, addOrderService.getBasketItems().size());
        Assertions.assertEquals(2, addOrderService.getBasketItems().get(0).getProductId());
    }

    @Test
    void getPricePerItem_shouldReturnPriceOfProduct() {
        // Arrange
        Product product = new Product();
        product.setProductId(1);
        product.setPrice(20);
        List<Product> products = new ArrayList<>();
        products.add(product);
        Mockito.when(productRepository.findByProductId(1)).thenReturn(products);

        // Act
        int price = addOrderService.getPricePerItem(1);

        // Assert
        Assertions.assertEquals(20, price);
    }

    @Test
    void removeAmountFromBasket_shouldRemoveAmountFromBasket() {
        // Arrange
        Orderline orderline = new Orderline();
        orderline.setProductId(1);
        orderline.setAmount(2);
        orderline.setCost(0);
        List<Orderline> basketOrderLines = new ArrayList<>();
        basketOrderLines.add(orderline);
        addOrderService.basketOrderLines = basketOrderLines;

        // Act
        addOrderService.removeAmountFromBasket(1);

        // Assert
        Assertions.assertEquals(1, addOrderService.getBasketItems().size());
        Assertions.assertEquals(1, addOrderService.getBasketItems().get(0).getAmount());
        Assertions.assertEquals(0, addOrderService.getBasketItems().get(0).getCost());
    }

    @Test
    void findById_shouldReturnProductById() {
        // Arrange
        Product product1 = new Product();
        product1.setProductId(1);
        Product product2 = new Product();
        product2.setProductId(2);
        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        // Act
        Product foundProduct = addOrderService.findById(products, 2);

        // Assert
        Assertions.assertEquals(2, foundProduct.getProductId());
    }

    @Test
    void addAmountInBasket_shouldAddAmountInBasket() {
        // Arrange
        Product product = new Product();
        product.setProductId(1);
        product.setPrice(10);
        product.setAvailable(5);
        List<Product> products = new ArrayList<>();
        products.add(product);
        Mockito.when(productRepository.findByProductId(1)).thenReturn(products);

        Orderline orderline = new Orderline();
        orderline.setProductId(1);
        orderline.setAmount(1);
        List<Orderline> basketOrderLines = new ArrayList<>();
        basketOrderLines.add(orderline);
        addOrderService.basketOrderLines = basketOrderLines;

        // Act
        addOrderService.addAmountInBasket(1);

        // Assert
        Assertions.assertEquals(2, addOrderService.getBasketItems().get(0).getAmount());
        Assertions.assertEquals(20, addOrderService.getBasketItems().get(0).getCost());
    }
}

