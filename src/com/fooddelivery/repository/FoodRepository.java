package com.fooddelivery.repository;

import com.fooddelivery.model.Food;
import java.util.ArrayList;

public class FoodRepository {

    private ArrayList<Food> foods = new ArrayList<>();

    public void addFood(Food food) {
        foods.add(food);
    }

    public ArrayList<Food> getAllFoods() {
        return foods;
    }

    public Food getFoodById(int id) {

        for (Food f : foods) {
            if (f.getFoodId() == id) {
                return f;
            }
        }

        return null;
    }

    public boolean updateFood(int id, String name,
                              double price, String category,
                              boolean available) {

        Food f = getFoodById(id);

        if (f != null) {
            f.setFoodName(name);
            f.setPrice(price);
            f.setCategory(category);
            f.setAvailable(available);
            return true;
        }

        return false;
    }

    public boolean deleteFood(int id) {

        Food f = getFoodById(id);

        if (f != null) {
            foods.remove(f);
            return true;
        }

        return false;
    }
}
