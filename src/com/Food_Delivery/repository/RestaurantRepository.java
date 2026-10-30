package com.Food_Delivery.repository;

import com.Food_Delivery.model.Restaurant;
import java.util.ArrayList;

public class RestaurantRepository {

    private ArrayList<Restaurant> restaurants = new ArrayList<>();

    public void addRestaurant(Restaurant restaurant) {
        restaurants.add(restaurant);
    }

    public ArrayList<Restaurant> getAllRestaurants() {
        return restaurants;
    }

    public Restaurant getRestaurantById(int id) {

        for (Restaurant r : restaurants) {
            if (r.getRestaurantId() == id) {
                return r;
            }
        }

        return null;
    }

    public boolean updateRestaurant(int id, String name,
                                    String location, String phone) {

        Restaurant r = getRestaurantById(id);

        if (r != null) {
            r.setName(name);
            r.setLocation(location);
            r.setPhone(phone);
            return true;
        }

        return false;
    }

    public boolean deleteRestaurant(int id) {

        Restaurant r = getRestaurantById(id);

        if (r != null) {
            restaurants.remove(r);
            return true;
        }

        return false;
    }
}
