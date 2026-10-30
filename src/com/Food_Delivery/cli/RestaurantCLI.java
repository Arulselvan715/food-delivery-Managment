package com.Food_Delivery.cli;

import com.Food_Delivery.model.Restaurant;
import com.Food_Delivery.model.Food;
import com.Food_Delivery.service.RestaurantService;
import com.Food_Delivery.service.FoodService;

import java.util.Scanner;

public class RestaurantCLI {

    private Scanner sc = new Scanner(System.in);

    private RestaurantService restaurantService;
    private FoodService foodService;

    public RestaurantCLI(RestaurantService restaurantService,
                         FoodService foodService) {

        this.restaurantService = restaurantService;
        this.foodService = foodService;
    }

    public void start() {

        while (true) {

            System.out.println("\n===== RESTAURANT & FOOD MANAGEMENT =====");
            System.out.println("1. Restaurant Management");
            System.out.println("2. Food Management");
            System.out.println("3. Exit");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    restaurantMenu();
                    break;

                case 2:
                    foodMenu();
                    break;

                case 3:
                    System.out.println("Thank you!");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // Restaurant Menu
    private void restaurantMenu() {

        while (true) {

            System.out.println("\n--- RESTAURANT MENU ---");
            System.out.println("1. Add Restaurant");
            System.out.println("2. View Restaurants");
            System.out.println("3. Search Restaurant");
            System.out.println("4. Update Restaurant");
            System.out.println("5. Delete Restaurant");
            System.out.println("6. Back");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Location: ");
                    String location = sc.nextLine();

                    System.out.print("Enter Phone: ");
                    String phone = sc.nextLine();

                    if (restaurantService.addRestaurant(
                            id, name, location, phone)) {
                        System.out.println("Restaurant added!");
                    } else {
                        System.out.println("Invalid details or ID already exists!");
                    }
                    break;

                case 2:
                    for (Restaurant r : restaurantService.viewRestaurants()) {
                        r.display();
                    }
                    break;

                case 3:
                    System.out.print("Enter Restaurant ID: ");
                    int searchId = sc.nextInt();

                    Restaurant r = restaurantService.searchRestaurant(searchId);

                    if (r != null) {
                        r.display();
                    } else {
                        System.out.println("Restaurant not found!");
                    }
                    break;

                case 4:
                    System.out.print("Enter Restaurant ID: ");
                    int updateId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter New Name: ");
                    String newName = sc.nextLine();

                    System.out.print("Enter New Location: ");
                    String newLocation = sc.nextLine();

                    System.out.print("Enter New Phone: ");
                    String newPhone = sc.nextLine();

                    if (restaurantService.updateRestaurant(
                            updateId, newName, newLocation, newPhone)) {
                        System.out.println("Restaurant updated!");
                    } else {
                        System.out.println("Restaurant not found!");
                    }
                    break;

                case 5:
                    System.out.print("Enter Restaurant ID: ");
                    int deleteId = sc.nextInt();

                    if (restaurantService.deleteRestaurant(deleteId)) {
                        System.out.println("Restaurant deleted!");
                    } else {
                        System.out.println("Restaurant not found!");
                    }
                    break;

                case 6:
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // Food Menu
    private void foodMenu() {

        while (true) {

            System.out.println("\n--- FOOD MENU ---");
            System.out.println("1. Add Food");
            System.out.println("2. View Foods");
            System.out.println("3. Search Food");
            System.out.println("4. Update Food");
            System.out.println("5. Delete Food");
            System.out.println("6. Back");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter Food ID: ");
                    int id = sc.nextInt();

                    System.out.print("Enter Restaurant ID: ");
                    int restaurantId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Food Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Price: ");
                    double price = sc.nextDouble();
                    sc.nextLine();

                    System.out.print("Enter Category: ");
                    String category = sc.nextLine();

                    System.out.print("Is Available? (true/false): ");
                    boolean available = sc.nextBoolean();

                    if (foodService.addFood(id, restaurantId, name,
                            price, category, available)) {
                        System.out.println("Food added!");
                    } else {
                        System.out.println("Invalid details, duplicate ID or restaurant not found!");
                    }
                    break;

                case 2:
                    for (Food f : foodService.viewFoods()) {
                        f.display();
                    }
                    break;

                case 3:
                    System.out.print("Enter Food ID: ");
                    int searchId = sc.nextInt();

                    Food f = foodService.searchFood(searchId);

                    if (f != null) {
                        f.display();
                    } else {
                        System.out.println("Food not found!");
                    }
                    break;

                case 4:
                    System.out.print("Enter Food ID: ");
                    int updateId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter New Food Name: ");
                    String newName = sc.nextLine();

                    System.out.print("Enter New Price: ");
                    double newPrice = sc.nextDouble();
                    sc.nextLine();

                    System.out.print("Enter New Category: ");
                    String newCategory = sc.nextLine();

                    System.out.print("Is Available? (true/false): ");
                    boolean newAvailability = sc.nextBoolean();

                    if (foodService.updateFood(updateId, newName,
                            newPrice, newCategory, newAvailability)) {
                        System.out.println("Food updated!");
                    } else {
                        System.out.println("Food not found!");
                    }
                    break;

                case 5:
                    System.out.print("Enter Food ID: ");
                    int deleteId = sc.nextInt();

                    if (foodService.deleteFood(deleteId)) {
                        System.out.println("Food deleted!");
                    } else {
                        System.out.println("Food not found!");
                    }
                    break;

                case 6:
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
