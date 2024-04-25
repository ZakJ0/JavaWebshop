package se.jaouhari.shop.entities;


import jakarta.persistence.*;

@Entity
public class Orderline {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int orderlineId;

    private int ordersId;

    private int customerId;

    private int productId;

    private int amount;

    private int cost;

    private String status;

    public Orderline(int ordersId, int customerId, int productId, int amount, int cost, String status) {
        this.ordersId = ordersId;
        this.customerId = customerId;
        this.productId = productId;
        this.amount = amount;
        this.cost = cost;
        this.status = status;
    }

    public Orderline() {
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getOrderlineId() {
        return orderlineId;
    }

    public void setOrderlineId(int orderlineid) {
        this.orderlineId = orderlineid;
    }

    public int getOrdersId() {
        return ordersId;
    }

    public void setOrderId(int orderdetailId) {
        this.ordersId = orderdetailId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Orderline{" +
                "orderlineId=" + orderlineId +
                ", ordersId=" + ordersId +
                ", customerId=" + customerId +
                ", productId=" + productId +
                ", amount=" + amount +
                ", cost=" + cost +
                ", status='" + status + '\'' +
                '}';
    }
}

