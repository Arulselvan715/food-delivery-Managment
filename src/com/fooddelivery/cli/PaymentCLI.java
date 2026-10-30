package com.fooddelivery.cli;

import com.fooddelivery.service.PaymentService;
import java.util.Scanner;

public class PaymentCLI {

    private PaymentService paymentService;
    private Scanner sc;

    public PaymentCLI(PaymentService paymentService) {
        this(paymentService, new Scanner(System.in));
    }

    public PaymentCLI(PaymentService paymentService, Scanner sc) {
        this.paymentService = paymentService;
        this.sc = sc;
    }

    public void showMenu() {

        int choice;

        do {
            System.out.println("\n===== PAYMENT MENU =====");
            System.out.println("1. Make Payment");
            System.out.println("2. View All Payments");
            System.out.println("3. Search Payment");
            System.out.println("4. Update Payment Status");
            System.out.println("5. Back to Main Menu");

            System.out.print("Enter your choice: ");
            if (!sc.hasNextInt()) {
                sc.nextLine();
                System.out.println("Invalid Choice!");
                choice = 0;
                continue;
            }
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Enter Payment ID: ");
                    int paymentId = sc.nextInt();

                    System.out.print("Enter Order ID: ");
                    int orderId = sc.nextInt();

                    System.out.print("Enter Amount: ");
                    double amount = sc.nextDouble();

                    sc.nextLine();

                    System.out.print("Enter Payment Method (UPI/Card/COD): ");
                    String method = sc.nextLine();

                    paymentService.makePayment(
                        paymentId, orderId, amount, method
                    );
                    break;

                case 2:
                    paymentService.viewPayments();
                    break;

                case 3:
                    System.out.print("Enter Payment ID: ");
                    int searchId = sc.nextInt();

                    paymentService.searchPayment(searchId);
                    break;

                case 4:
                    System.out.print("Enter Payment ID: ");
                    int updateId = sc.nextInt();

                    sc.nextLine();

                    System.out.print("Enter New Status: ");
                    String status = sc.nextLine();

                    paymentService.updateStatus(updateId, status);
                    break;

                case 5:
                    System.out.println("Exiting Payment Menu...");
                    break;

                default:
                    System.out.println("Invalid Choice!");
            }

        } while (choice != 5);
    }
}
