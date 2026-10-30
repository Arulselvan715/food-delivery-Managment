package com.fooddelivery.cli;

import com.fooddelivery.model.Order;
import com.fooddelivery.model.OrderItem;
import com.fooddelivery.model.OrderStatus;
import com.fooddelivery.service.OrderService;

import java.util.Scanner;

public class OrderCLI {

    private OrderService orderService;
    private Scanner sc;

    public OrderCLI(OrderService orderService) {
        this(orderService, new Scanner(System.in));
    }

    public OrderCLI(OrderService orderService, Scanner sc) {
        this.orderService = orderService;
        this.sc = sc;
    }

    public void showMenu() {

        int choice;

        do {
            System.out.println("\n===== ORDER MANAGEMENT =====");
            System.out.println("1. Place Order");
            System.out.println("2. View All Orders");
            System.out.println("3. Search & View Order");
            System.out.println("4. Update Order Status");
            System.out.println("5. Cancel Order");
            System.out.println("6. Reject Order");
            System.out.println("7. Back to Main Menu");

            System.out.print("Enter Choice: ");
            if (!sc.hasNextInt()) {
                sc.nextLine();
                System.out.println("Invalid Choice!");
                choice = 0;
                continue;
            }
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter Order ID: ");
                    int orderId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Customer Name: ");
                    String customerName = sc.nextLine();

                    System.out.print("Enter Restaurant Name: ");
                    String restaurantName = sc.nextLine();

                    Order order = new Order(orderId, customerName, restaurantName);

                    System.out.print("How many items to add: ");
                    int itemCount = sc.nextInt();
                    sc.nextLine();

                    for (int i = 1; i <= itemCount; i++) {
                        System.out.println("Item " + i + ":");
                        System.out.print("  Name: ");
                        String itemName = sc.nextLine();
                        System.out.print("  Quantity: ");
                        int qty = sc.nextInt();
                        System.out.print("  Price: ");
                        double price = sc.nextDouble();
                        sc.nextLine();

                        order.addItem(new OrderItem(itemName, qty, price));
                    }

                    orderService.placeOrder(order, true, true, 0.0, true);
                    break;

                case 2:
                    if (orderService.getOrders().isEmpty()) {
                        System.out.println("No orders found!");
                    } else {
                        for (Order o : orderService.getOrders()) {
                            orderService.displayOrder(o);
                        }
                    }
                    break;

                case 3:
                    System.out.print("Enter Order ID: ");
                    int searchId = sc.nextInt();
                    sc.nextLine();

                    Order found = orderService.getOrderById(searchId);
                    if (found != null) {
                        orderService.displayOrder(found);
                    } else {
                        System.out.println("Order not found!");
                    }
                    break;

                case 4:
                    System.out.print("Enter Order ID: ");
                    int updateId = sc.nextInt();
                    sc.nextLine();

                    Order toUpdate = orderService.getOrderById(updateId);
                    if (toUpdate == null) {
                        System.out.println("Order not found!");
                        break;
                    }

                    System.out.println("Choose new status:");
                    System.out.println("1. ACCEPTED");
                    System.out.println("2. PREPARING");
                    System.out.println("3. READY_FOR_PICKUP");
                    System.out.println("4. OUT_FOR_DELIVERY");
                    System.out.println("5. DELIVERED");
                    System.out.print("Choice: ");
                    int statusChoice = sc.nextInt();
                    sc.nextLine();

                    OrderStatus newStatus = switch (statusChoice) {
                        case 1 -> OrderStatus.ACCEPTED;
                        case 2 -> OrderStatus.PREPARING;
                        case 3 -> OrderStatus.READY_FOR_PICKUP;
                        case 4 -> OrderStatus.OUT_FOR_DELIVERY;
                        case 5 -> OrderStatus.DELIVERED;
                        default -> null;
                    };

                    if (newStatus != null) {
                        orderService.updateStatus(toUpdate, newStatus);
                    } else {
                        System.out.println("Invalid status choice!");
                    }
                    break;

                case 5:
                    System.out.print("Enter Order ID to cancel: ");
                    int cancelId = sc.nextInt();
                    sc.nextLine();

                    Order toCancel = orderService.getOrderById(cancelId);
                    if (toCancel != null) {
                        orderService.cancelOrder(toCancel);
                    } else {
                        System.out.println("Order not found!");
                    }
                    break;

                case 6:
                    System.out.print("Enter Order ID to reject: ");
                    int rejectId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Reason for rejection: ");
                    String reason = sc.nextLine();

                    Order toReject = orderService.getOrderById(rejectId);
                    if (toReject != null) {
                        orderService.rejectOrder(toReject, reason);
                    } else {
                        System.out.println("Order not found!");
                    }
                    break;

                case 7:
                    System.out.println("Exiting Order Menu...");
                    break;

                default:
                    System.out.println("Invalid Choice!");
            }

        } while (choice != 7);
    }
}
