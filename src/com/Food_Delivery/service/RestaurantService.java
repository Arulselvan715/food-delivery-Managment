package com.Food_Delivery.service;

import com.Food_Delivery.model.Restaurant;
import com.Food_Delivery.repository.RestaurantRepository;
import java.util.ArrayList;

public class RestaurantService {

    private RestaurantRepository repository;

    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    public boolean addRestaurant(int id, String name,
                                 String location, String phone) {

        if (id <= 0 || name.isEmpty() || location.isEmpty()
                || phone.isEmpty()) {
            return false;
        }

        if (repository.getRestaurantById(id) != null) {
            return false;
        }

        Restaurant restaurant =
            new Restaurant(id, name, location, phone);

        repository.addRestaurant(restaurant);

        return true;
    }

    public ArrayList<Restaurant> viewRestaurants() {
        return repository.getAllRestaurants();
    }

    public Restaurant searchRestaurant(int id) {
        return repository.getRestaurantById(id);
    }

    public boolean updateRestaurant(int id, String name,
                                    String location, String phone) {
        return repository.updateRestaurant(id, name, location, phone);
    }

    public boolean deleteRestaurant(int id) {
        return repository.deleteRestaurant(id);
    }
}
