package se.jaouhari.shop.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "orders")
public class Order {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="id_orderdetails")
    private int orderId;

    private int customerId;

    private Timestamp time;

    private double totalCost;

    private String status;

    public Order() {
    }

    public Order(int customerId, Timestamp time, double totalCost, String status) {
        this.customerId = customerId;
        this.time = time;
        this.totalCost = totalCost;
        this.status=status;
    }


    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerid) {
        this.customerId = customerid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalcost) {
        this.totalCost = totalcost;
    }

}
