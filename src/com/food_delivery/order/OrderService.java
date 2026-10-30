package com.food_delivery.order;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class OrderService {

    private ArrayList<Order> orders = new ArrayList<>();

    // Timer for automatic cancellation
    private ScheduledExecutorService scheduler =
            Executors.newSingleThreadScheduledExecutor(r -> {

                Thread thread = new Thread(r);
                thread.setDaemon(true);
                return thread;

            });

    // Place order
    public void placeOrder(Order order,
                           boolean restaurantOpen,
                           boolean itemsAvailable,
                           double minimumAmount,
                           boolean addressServiceable) {

        if (!restaurantOpen) {
            System.out.println("Restaurant is closed!");
            return;
        }

        if (!itemsAvailable) {
            System.out.println("Food items unavailable!");
            return;
        }

        if (order.getTotalAmount() < minimumAmount) {
            System.out.println("Minimum order amount not reached!");
            return;
        }

        if (!addressServiceable) {
            System.out.println("Delivery address not serviceable!");
            return;
        }

        orders.add(order);

        System.out.println("Order placed successfully!");
        System.out.println("Order ID: " + order.getOrderId());

        // Automatically cancel if not accepted within 5 minutes
        scheduler.schedule(() -> {

            synchronized (order) {

                if (order.getStatus() == OrderStatus.PLACED) {

                    order.setStatus(OrderStatus.CANCELLED);

                    System.out.println(
                            "Order " + order.getOrderId()
                            + " automatically cancelled!"
                    );
                }
            }

        }, 5, TimeUnit.MINUTES);
    }

    // Update order status
    public void updateStatus(Order order, OrderStatus newStatus) {

        synchronized (order) {

            boolean valid = false;

            switch (order.getStatus()) {

                case PLACED:
                    valid = newStatus == OrderStatus.ACCEPTED;
                    break;

                case ACCEPTED:
                    valid = newStatus == OrderStatus.PREPARING;
                    break;

                case PREPARING:
                    valid = newStatus == OrderStatus.READY_FOR_PICKUP;
                    break;

                case READY_FOR_PICKUP:
                    valid = newStatus == OrderStatus.OUT_FOR_DELIVERY;
                    break;

                case OUT_FOR_DELIVERY:
                    valid = newStatus == OrderStatus.DELIVERED;
                    break;
            }

            if (valid) {

                order.setStatus(newStatus);

                System.out.println("Status updated: " + newStatus);

            } else {

                System.out.println("Invalid status change!");
            }
        }
    }

    // Customer cancels order
    public void cancelOrder(Order order) {

        synchronized (order) {

            if (order.getStatus() == OrderStatus.PLACED ||
                order.getStatus() == OrderStatus.ACCEPTED) {

                order.setStatus(OrderStatus.CANCELLED);

                System.out.println("Order cancelled successfully!");

            } else {

                System.out.println("Order cannot be cancelled!");
            }
        }
    }

    // Restaurant rejects order
    public void rejectOrder(Order order, String reason) {

        synchronized (order) {

            if (order.getStatus() == OrderStatus.PLACED &&
                reason != null && !reason.isBlank()) {

                order.setStatus(OrderStatus.REJECTED);

                System.out.println("Order rejected!");
                System.out.println("Reason: " + reason);

            } else {

                System.out.println("Order cannot be rejected!");
            }
        }
    }

    // Display order details
    public void displayOrder(Order order) {

        System.out.println("\n----- ORDER DETAILS -----");

        System.out.println("Order ID: " + order.getOrderId());
        System.out.println("Customer: " + order.getCustomerName());
        System.out.println("Restaurant: " + order.getRestaurantName());

        System.out.println("\nItems:");

        for (OrderItem item : order.getItems()) {

            System.out.println(
                    item.getItemName()
                    + " | Quantity: " + item.getQuantity()
                    + " | Price: " + item.getPrice()
                    + " | Total: " + item.getTotal()
            );
        }

        System.out.println("\nTotal Amount: " + order.getTotalAmount());
        System.out.println("Status: " + order.getStatus());
        System.out.println("Order Time: " + order.getOrderTime());
        System.out.println("Updated Time: " + order.getUpdatedTime());

        System.out.println("-------------------------");
    }

    // Get all orders
    public ArrayList<Order> getOrders() {
        return orders;
    }

    // Stop scheduler when application shuts down
    public void close() {
        scheduler.shutdownNow();
    }
}