package com.fooddelivery.cli;

import com.fooddelivery.service.DeliveryService;
import java.util.Scanner;

public class DeliveryCLI {

    private DeliveryService deliveryService;
    private Scanner sc;

    public DeliveryCLI(DeliveryService deliveryService) {
        this(deliveryService, new Scanner(System.in));
    }

    public DeliveryCLI(DeliveryService deliveryService, Scanner sc) {
        this.deliveryService = deliveryService;
        this.sc = sc;
    }

    public void showMenu() {

        int choice;

        do {
            System.out.println("\n===== DELIVERY MENU =====");
            System.out.println("1. Add Delivery");
            System.out.println("2. View All Deliveries");
            System.out.println("3. Search Delivery");
            System.out.println("4. Update Delivery Status");
            System.out.println("5. Back to Main Menu");

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
                    System.out.print("Enter Delivery ID: ");
                    int deliveryId = sc.nextInt();

                    System.out.print("Enter Order ID: ");
                    int orderId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Delivery Address: ");
                    String address = sc.nextLine();

                    System.out.print("Enter Delivery Partner: ");
                    String partner = sc.nextLine();

                    deliveryService.addDelivery(
                            deliveryId, orderId, address, partner
                    );
                    break;

                case 2:
                    deliveryService.viewDeliveries();
                    break;

                case 3:
                    System.out.print("Enter Delivery ID: ");
                    int searchId = sc.nextInt();

                    deliveryService.searchDelivery(searchId);
                    break;

                case 4:
                    System.out.print("Enter Delivery ID: ");
                    int updateId = sc.nextInt();
                    sc.nextLine();

                    System.out.println("1. Processing");
                    System.out.println("2. Shipped");
                    System.out.println("3. Out for Delivery");
                    System.out.println("4. Delivered");

                    System.out.print("Enter New Status: ");
                    String status = sc.nextLine();

                    deliveryService.updateStatus(updateId, status);
                    break;

                case 5:
                    System.out.println("Exiting Delivery Menu...");
                    break;

                default:
                    System.out.println("Invalid Choice!");
            }

        } while (choice != 5);
    }
}
