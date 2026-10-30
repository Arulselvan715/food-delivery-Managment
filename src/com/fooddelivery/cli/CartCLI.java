package com.fooddelivery.cli;

import com.fooddelivery.model.Address;
import com.fooddelivery.model.Customer;
import com.fooddelivery.service.CartService;
import com.fooddelivery.service.CustomerService;
import com.fooddelivery.service.DemoCatalog;

import java.util.List;
import java.util.Scanner;

public class CartCLI {
    private final Scanner scanner;
    private final CustomerService customerService;
    private final CartService cartService;
    private final DemoCatalog catalog;

    public CartCLI(CustomerService customerService, CartService cartService, DemoCatalog catalog) {
        this(customerService, cartService, catalog, new Scanner(System.in));
    }

    public CartCLI(CustomerService customerService, CartService cartService, DemoCatalog catalog, Scanner scanner) {
        this.customerService = customerService;
        this.cartService = cartService;
        this.catalog = catalog;
        this.scanner = scanner;
    }

    public void start() {
        while (true) {
            System.out.println("\n===== CUSTOMER & CART MANAGEMENT =====");
            System.out.println("1. Register customer");
            System.out.println("2. Customer menu");
            System.out.println("3. Show demo restaurants and menu");
            System.out.println("0. Back to Main Menu");
            int choice = readInt("Enter choice: ");
            try {
                switch (choice) {
                    case 1:
                        registerCustomer();
                        break;
                    case 2:
                        customerMenu();
                        break;
                    case 3:
                        catalog.printCatalog();
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Invalid choice.");
                        break;
                }
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void registerCustomer() {
        System.out.println("\n--- CUSTOMER REGISTRATION ---");
        String name = readText("Name: ");
        String phone = readText("Phone (10 digits): ");
        String email = readText("Email: ");
        Address address = readAddress();
        Customer customer = customerService.register(name, phone, email, List.of(address));
        System.out.println("Registered successfully. Customer ID: " + customer.getId());
    }

    private void customerMenu() {
        long customerId = readLong("Enter customer ID: ");
        Customer customer = customerService.getCustomer(customerId);
        while (true) {
            System.out.println("\n--- CUSTOMER MENU (" + customer.getName() + ") ---");
            System.out.println("1. View profile and addresses");
            System.out.println("2. Add address");
            System.out.println("3. Remove address");
            System.out.println("4. Add item to cart");
            System.out.println("5. View cart");
            System.out.println("6. Update item quantity");
            System.out.println("7. Remove item");
            System.out.println("8. Clear cart");
            System.out.println("9. Validate checkout");
            System.out.println("0. Back");
            int choice = readInt("Enter choice: ");
            try {
                switch (choice) {
                    case 1:
                        showProfile(customerId);
                        break;
                    case 2:
                        Address a = customerService.addAddress(customerId, readAddress());
                        System.out.println("Address added. Address ID: " + a.getId());
                        break;
                    case 3:
                        customerService.removeAddress(customerId, readLong("Address ID: "));
                        System.out.println("Address removed.");
                        break;
                    case 4:
                        addItem(customerId);
                        break;
                    case 5:
                        System.out.println(cartService.viewCart(customerId));
                        break;
                    case 6:
                        cartService.updateQuantity(customerId,
                                readLong("Menu item ID: "), readInt("New quantity (1-10): "));
                        System.out.println("Quantity updated.");
                        break;
                    case 7:
                        cartService.removeItem(customerId, readLong("Menu item ID: "));
                        System.out.println("Item removed from cart.");
                        break;
                    case 8:
                        cartService.clearCart(customerId);
                        System.out.println("Cart cleared.");
                        break;
                    case 9:
                        checkoutValidation(customerId);
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Invalid choice.");
                        break;
                }
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void showProfile(long customerId) {
        Customer c = customerService.getCustomer(customerId);
        System.out.println("\nID: " + c.getId() + "\nName: " + c.getName()
                + "\nPhone: " + c.getPhone() + "\nEmail: " + c.getEmail());
        c.getAddresses().forEach(System.out::println);
    }

    private void addItem(long customerId) {
        catalog.printCatalog();
        long restaurantId = readLong("Restaurant ID: ");
        long itemId = readLong("Menu item ID: ");
        int quantity = readInt("Quantity (1-10): ");
        cartService.addItem(customerId, restaurantId, itemId, quantity);
        System.out.println("Item added to cart.");
    }

    private void checkoutValidation(long customerId) {
        showProfile(customerId);
        long addressId = readLong("Choose address ID: ");
        var result = cartService.validateCheckout(customerId, addressId);
        result.notifications().forEach(n -> System.out.println("NOTICE: " + n));
        if (result.valid()) {
            System.out.println("Checkout validation passed.");
            System.out.println("Items subtotal: Rs. " + result.itemsSubtotal());
            System.out.println("Now pass the cart to Order and Payment services.");
        } else {
            System.out.println("Checkout rejected: " + result.message());
        }
    }

    private Address readAddress() {
        System.out.println("Delivery address:");
        return new Address(0, readText("Label (Home/Office): "),
                readText("House/Flat: "), readText("Street: "),
                readText("City: "), readText("State: "), readText("Pincode: "));
    }

    private String readText(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readText(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid integer.");
            }
        }
    }

    private long readLong(String prompt) {
        while (true) {
            try {
                return Long.parseLong(readText(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number.");
            }
        }
    }
}
