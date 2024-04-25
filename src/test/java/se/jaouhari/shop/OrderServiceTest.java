package se.jaouhari.shop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import se.jaouhari.shop.entities.Order;
import se.jaouhari.shop.entities.User;
import se.jaouhari.shop.repositories.OrderRepository;
import se.jaouhari.shop.repositories.UserRepo;
import se.jaouhari.shop.services.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class OrderServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getOrders_shouldReturnOrdersForGivenUserName() {
        // Arrange
        String username = "testUser";
        User user = new User();
        user.setId(1);
        List<User> users = new ArrayList<>();
        users.add(user);
        Mockito.when(userRepo.findUserByUsername(username)).thenReturn(users);

        List<Order> orders = new ArrayList<>();
        Order order = new Order();
        order.setCustomerId(1);
        orders.add(order);
        Mockito.when(orderRepository.findOrderByCustomerId(1)).thenReturn(orders);

        // Act
        List<Order> result = orderService.getOrders(username);

        // Assert
        Assertions.assertEquals(1, result.size());
    }

    @Test
    void updateStatus_shouldUpdateOrderStatusWhenOrderExists() {
        int orderId = 1;
        String status = "shipped";
        Order order = new Order();
        order.setOrderId(orderId);
        Mockito.when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        String result = orderService.updateStatus(orderId, status);

        Assertions.assertEquals("Order 1 status updated successfully", result);
        Assertions.assertEquals(status, order.getStatus());
        Mockito.verify(orderRepository, Mockito.times(1)).save(order);
    }

    @Test
    void getByStatus_shouldReturnOrdersWithPendingStatus() {
        List<Order> orders = new ArrayList<>();
        Order order = new Order();
        order.setStatus("pending");
        orders.add(order);
        Mockito.when(orderRepository.findByStatus("pending")).thenReturn(orders);

        List<Order> result = orderService.getByStatus();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("pending", result.get(0).getStatus());
    }

    @Test
    void getAllOrders_shouldReturnAllOrders() {
        List<Order> orders = new ArrayList<>();
        Order order1 = new Order();
        Order order2 = new Order();
        orders.add(order1);
        orders.add(order2);
        Mockito.when(orderRepository.findAll()).thenReturn(orders);

        List<Order> result = orderService.getAllOrders();

        Assertions.assertEquals(2, result.size());
    }
}

