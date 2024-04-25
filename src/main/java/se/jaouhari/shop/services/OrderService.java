package se.jaouhari.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.jaouhari.shop.entities.Order;
import se.jaouhari.shop.entities.User;
import se.jaouhari.shop.repositories.OrderRepository;
import se.jaouhari.shop.repositories.UserRepo;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    OrderRepository orderRepository;


    public List<Order> getOrders(String name) {
        List<Order> order = new ArrayList<>();
        List<User> customer = userRepo.findUserByUsername(name);
        for (User cust : customer) {
            List<Order> orders = orderRepository.findOrderByCustomerId(cust.getId());
            order.addAll(orders);
        }
        return order;
    }

    public String updateStatus(int id, String status) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        Order order = orderOptional.get();
        if (!orderOptional.isPresent()) {
            return "Order was not found";
        }
        order.setStatus(status);
        orderRepository.save(order);
        return "Order " + id + " status updated successfully";
    }

    public List<Order> getByStatus() {
        return orderRepository.findByStatus("pending");
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

}

