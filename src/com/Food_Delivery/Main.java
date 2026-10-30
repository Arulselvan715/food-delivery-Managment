package com.Food_Delivery;

import com.Food_Delivery.cli.RestaurantCLI;
import com.Food_Delivery.repository.FoodRepository;
import com.Food_Delivery.repository.RestaurantRepository;
import com.Food_Delivery.service.FoodService;
import com.Food_Delivery.service.RestaurantService;

public class Main {

    public static void main(String[] args) {

        // Create Repository objects
        RestaurantRepository restaurantRepository =
            new RestaurantRepository();

        FoodRepository foodRepository =
            new FoodRepository();

        // Create Service objects
        RestaurantService restaurantService =
            new RestaurantService(restaurantRepository);

        FoodService foodService =
            new FoodService(foodRepository, restaurantRepository);

        // Create CLI object
        RestaurantCLI cli =
            new RestaurantCLI(restaurantService, foodService);

        // Start application
        cli.start();
    }
}
