package com.fooddelivery;

import com.fooddelivery.cli.CartCLI;
import com.fooddelivery.cli.DeliveryCLI;
import com.fooddelivery.cli.OrderCLI;
import com.fooddelivery.cli.PaymentCLI;
import com.fooddelivery.cli.RestaurantCLI;
import com.fooddelivery.cli.UserCLI;
import com.fooddelivery.repository.CartRepository;
import com.fooddelivery.repository.CustomerRepository;
import com.fooddelivery.repository.DeliveryRepository;
import com.fooddelivery.repository.FoodRepository;
import com.fooddelivery.repository.PaymentRepository;
import com.fooddelivery.repository.RestaurantRepository;
import com.fooddelivery.repository.UserRepository;
import com.fooddelivery.service.ActiveOrderChecker;
import com.fooddelivery.service.CartService;
import com.fooddelivery.service.CustomerService;
import com.fooddelivery.service.DeliveryService;
import com.fooddelivery.service.DemoCatalog;
import com.fooddelivery.service.FoodService;
import com.fooddelivery.service.OrderService;
import com.fooddelivery.service.PaymentService;
import com.fooddelivery.service.RestaurantService;
import com.fooddelivery.service.UserService;

import java.util.Scanner;

/**
 * Unified Application Entry Point for Food Delivery Management System.
 * Connects all modules developed by the team.
 */
public class Main {

    public static void main(String[] args) {
        // 1. Repositories
        UserRepository userRepository = new UserRepository();
        RestaurantRepository restaurantRepository = new RestaurantRepository();
        FoodRepository foodRepository = new FoodRepository();
        CustomerRepository customerRepository = new CustomerRepository();
        CartRepository cartRepository = new CartRepository();
        DeliveryRepository deliveryRepository = new DeliveryRepository();
        PaymentRepository paymentRepository = new PaymentRepository();

        // 2. Services
        UserService userService = new UserService(userRepository);
        RestaurantService restaurantService = new RestaurantService(restaurantRepository);
        FoodService foodService = new FoodService(foodRepository, restaurantRepository);
        OrderService orderService = new OrderService();
        DemoCatalog demoCatalog = new DemoCatalog();

        // Connect Module 2 (Cart) with Module 3 (Order) via ActiveOrderChecker
        ActiveOrderChecker activeOrderChecker = customerId -> {
            // Count active orders from order service
            return customerRepository.findById(customerId)
                    .map(c -> orderService.countActiveOrders(c.getName()))
                    .orElse(0);
        };

        CustomerService customerService = new CustomerService(customerRepository);
        CartService cartService = new CartService(cartRepository, customerRepository, demoCatalog, activeOrderChecker);
        DeliveryService deliveryService = new DeliveryService(deliveryRepository);
        PaymentService paymentService = new PaymentService(paymentRepository);

        Scanner scanner = new Scanner(System.in);

        // 3. CLIs (sharing the same Scanner to prevent stdin buffer desync)
        UserCLI userCLI = new UserCLI(userService, scanner);
        RestaurantCLI restaurantCLI = new RestaurantCLI(restaurantService, foodService, scanner);
        CartCLI cartCLI = new CartCLI(customerService, cartService, demoCatalog, scanner);
        OrderCLI orderCLI = new OrderCLI(orderService, scanner);
        DeliveryCLI deliveryCLI = new DeliveryCLI(deliveryService, scanner);
        PaymentCLI paymentCLI = new PaymentCLI(paymentService, scanner);

        while (true) {
            System.out.println("\n==============================================");
            System.out.println("   FOOD DELIVERY MANAGEMENT SYSTEM (MAIN)");
            System.out.println("==============================================");
            System.out.println("1. User Management (Register / Login / Profile)");
            System.out.println("2. Restaurant & Food Management");
            System.out.println("3. Customer & Cart Management");
            System.out.println("4. Order Management");
            System.out.println("5. Payment Management");
            System.out.println("6. Delivery Tracking");
            System.out.println("0. Exit Application");
            System.out.print("Enter your choice: ");

            if (!scanner.hasNextInt()) {
                scanner.nextLine();
                System.out.println("Invalid choice! Please enter a number.");
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    userCLI.showMenu();
                    break;
                case 2:
                    restaurantCLI.start();
                    break;
                case 3:
                    cartCLI.start();
                    break;
                case 4:
                    orderCLI.showMenu();
                    break;
                case 5:
                    paymentCLI.showMenu();
                    break;
                case 6:
                    deliveryCLI.showMenu();
                    break;
                case 0:
                    System.out.println("Shutting down Food Delivery Management System. Goodbye!");
                    orderService.close();
                    return;
                default:
                    System.out.println("Invalid option! Please select between 0 and 6.");
            }
        }
    }
}
