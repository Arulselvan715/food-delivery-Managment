package com.food_delivery.order;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Order {

    private int orderId;
    private String customerName;
    private String restaurantName;

    private ArrayList<OrderItem> items = new ArrayList<>();

    private double totalAmount;

    private OrderStatus status;

    private LocalDateTime orderTime;
    private LocalDateTime updatedTime;

    public Order(int orderId, String customerName, String restaurantName) {

        this.orderId = orderId;
        this.customerName = customerName;
        this.restaurantName = restaurantName;

        this.status = OrderStatus.PLACED;

        this.orderTime = LocalDateTime.now();
        this.updatedTime = orderTime;
    }

    // Add item to order
    public void addItem(OrderItem item) {

        items.add(item);

        totalAmount += item.getTotal();
    }

    // Get order details
    public int getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public ArrayList<OrderItem> getItems() {
        return items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    // Update status
    void setStatus(OrderStatus status) {

        this.status = status;

        this.updatedTime = LocalDateTime.now();
    }
}