package se.jaouhari.shop.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import se.jaouhari.shop.entities.Order;
import se.jaouhari.shop.entities.Orderline;
import se.jaouhari.shop.entities.Product;
import se.jaouhari.shop.entities.User;
import se.jaouhari.shop.repositories.OrderLineRepository;
import se.jaouhari.shop.repositories.OrderRepository;
import se.jaouhari.shop.repositories.ProductRepository;
import se.jaouhari.shop.repositories.UserRepo;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@SessionScope
public class AddOrderService {

    @Autowired
    OrderLineRepository orderLineRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepo userRepo;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    EmailSender emailSender;


    List<Orderline> basketOrderLines = new ArrayList<>();
    List<Order> basketDetails = new ArrayList<>();

    public void addToBasket(int id) {
        List<Product> product = productRepository.findByProductId(id);
        for (Product electronic : product) {
            boolean found = false;
            for (Orderline existingOrder : basketOrderLines) {
                if (existingOrder.getProductId() == id) {
                    existingOrder.setAmount(existingOrder.getAmount() + 1);
                    existingOrder.setCost(electronic.getPrice() * existingOrder.getAmount());
                    found = true;
                    break;
                }
            }

            if (product != null && !found) {
                Orderline orderline = new Orderline();
                orderline.setAmount(1);
                orderline.setProductId(id);
                orderline.setStatus("pending");
                orderline.setCost(electronic.getPrice());
                basketOrderLines.add(orderline);
            }
        }
    }

    public String setOrder(String username, String password) {
        if (basketOrderLines.isEmpty()) {
            return "Basket is empty";
        }

        List<User> findCustomer = userRepo.findByUsernameAndPassword(username, password);
        if (findCustomer.isEmpty()) {
            return "Could not find customer";
        }

        for (User user : findCustomer) {
            Order order = new Order();
            order.setCustomerId(user.getId());
            order.setTime(Timestamp.valueOf(LocalDateTime.now()));
            order.setStatus("pending");

            int totalCost = 0;

            for (Orderline orderline : basketOrderLines) {
                List<Product> products = productRepository.findByProductId(orderline.getProductId());
                for (Product e : products) {
                    int available = e.getAvailable();
                    int orderAmount = orderline.getAmount();
                    if (orderAmount <= available) {
                        order.setTotalCost(order.getTotalCost() + orderline.getCost());
                        totalCost += orderline.getCost();

                        e.setAvailable(available - orderAmount);
                    }
                }
            }

            orderRepository.save(order);

            for (Orderline item : basketOrderLines) {
                item.setOrderId(order.getOrderId());
                item.setCustomerId(order.getCustomerId());
                orderLineRepository.save(item);
            }

            emailSender.sendOrderConfirmationEmail(username);
            basketOrderLines.clear();
            basketDetails.clear();
            return "Items were ordered";
        }
        return "Unexpected error occurred";
    }

    public List<Orderline> getBasketItems() {
        return basketOrderLines;
    }

    public int getTotalCost() {
        int totalCost = 0;
        for (int i = 0; i < basketOrderLines.size(); i++) {
            totalCost += basketOrderLines.get(i).getCost();
        }
        System.out.println(totalCost);
        return totalCost;
    }

    public int getAvailablility(int id) {
        List<Product> product = productRepository.findByProductId(id);
        for (Product e : product) {
            return e.getAvailable();
        }
        return -1;
    }

    public List<Orderline> removeItemFromBasket(int input) {
        for (int i = 0; i < basketOrderLines.size(); i++) {
            if (input == basketOrderLines.get(i).getProductId()) {
                basketOrderLines.remove(i);
                break;

            }
        }
        return basketOrderLines;
    }

    public int getPricePerItem(int id) {
        List<Product> findProduct = productRepository.findByProductId(id);
        if (findProduct != null && !findProduct.isEmpty()) {
            for (int i = 0; true; i++) {
                Product product = findProduct.get(i);
                return product.getPrice();
            }
        }
        return 0;
    }

    public List<Orderline> removeAmountFromBasket(int productId) {
        int price = getPricePerItem(productId);
        for (int i = 0; i < basketOrderLines.size(); i++) {
            Orderline item = basketOrderLines.get(i);
            if (productId == item.getProductId()) {
                int productAmountInBasket = item.getAmount() - 1;
                int newPrice = price * productAmountInBasket;
                if (productAmountInBasket <= 0) {
                    basketOrderLines.remove(i);
                }
                else {
                    item.setAmount(productAmountInBasket);
                    item.setCost(newPrice);
                    for (Orderline orderline : basketOrderLines) {
                        if (orderline.getProductId() == item.getProductId()) {
                            orderline.setAmount(productAmountInBasket);
                            break;
                        }
                    }
                }
            }
        }
        return basketOrderLines;
    }

    public Product findById(List<Product> products, int id) {
        for (Product product : products) {
            if (product.getProductId() == id) {
                return product;
            }
        }
        return null;
    }

    public List<Orderline> addAmountInBasket(int productId) {
        int price = getPricePerItem(productId);
        List<Product> product = productRepository.findByProductId(productId);

        for (int i = 0; i < basketOrderLines.size(); i++) {
            Orderline item = basketOrderLines.get(i);

            if (productId == item.getProductId()) {
                int productAmountInBasket = item.getAmount() + 1;
                int newPrice = price * productAmountInBasket;

                Product e = findById(product, productId);
                if (e != null && e.getAvailable() >= productAmountInBasket) {
                    item.setAmount(productAmountInBasket);
                    item.setCost(newPrice);
                    for (Orderline orderline : basketOrderLines) {
                        if (orderline.getProductId() == item.getProductId()) {
                            orderline.setAmount(productAmountInBasket);
                            break;
                        }
                    }
                }
            }
        }
        return basketOrderLines;
    }
}