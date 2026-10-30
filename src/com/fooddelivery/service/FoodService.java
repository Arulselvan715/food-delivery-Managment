package com.fooddelivery.service;

import com.fooddelivery.model.Food;
import com.fooddelivery.repository.FoodRepository;
import com.fooddelivery.repository.RestaurantRepository;
import java.util.ArrayList;

public class FoodService {

    private FoodRepository foodRepository;
    private RestaurantRepository restaurantRepository;

    public FoodService(FoodRepository foodRepository,
                       RestaurantRepository restaurantRepository) {

        this.foodRepository = foodRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public boolean addFood(int id, int restaurantId,
                           String name, double price,
                           String category, boolean available) {

        if (id <= 0 || name.isEmpty() || category.isEmpty()
                || price <= 0) {
            return false;
        }

        if (foodRepository.getFoodById(id) != null) {
            return false;
        }

        if (restaurantRepository.getRestaurantById(restaurantId) == null) {
            return false;
        }

        Food food = new Food(id, restaurantId, name,
                             price, category, available);

        foodRepository.addFood(food);

        return true;
    }

    public ArrayList<Food> viewFoods() {
        return foodRepository.getAllFoods();
    }

    public Food searchFood(int id) {
        return foodRepository.getFoodById(id);
    }

    public boolean updateFood(int id, String name,
                              double price, String category,
                              boolean available) {

        return foodRepository.updateFood(
            id, name, price, category, available
        );
    }

    public boolean deleteFood(int id) {
        return foodRepository.deleteFood(id);
    }
}
