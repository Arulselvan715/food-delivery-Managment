package com.fooddeliverymanagement.service;

import com.fooddeliverymanagement.model.MenuItem;
import com.fooddeliverymanagement.model.Restaurant;
import java.math.BigDecimal;
import java.util.*;

/** Demo data so Module 2 can be run before Module 1 is merged. */
public class DemoCatalog {
    private final Map<Long, Restaurant> restaurants = new LinkedHashMap<>();
    private final Map<Long, MenuItem> items = new LinkedHashMap<>();

    public DemoCatalog() {
        restaurants.put(1L, new Restaurant(1, "Pollachi Meals", "Pollachi",
                new BigDecimal("100.00"), true));
        restaurants.put(2L, new Restaurant(2, "Coimbatore Pizza", "Coimbatore",
                new BigDecimal("150.00"), true));
        items.put(101L, new MenuItem(101, 1, "Veg Meals", new BigDecimal("120.00"), true));
        items.put(102L, new MenuItem(102, 1, "Paneer Rice", new BigDecimal("140.00"), true));
        items.put(103L, new MenuItem(103, 1, "Unavailable Sweet", new BigDecimal("50.00"), false));
        items.put(201L, new MenuItem(201, 2, "Margherita Pizza", new BigDecimal("220.00"), true));
        items.put(202L, new MenuItem(202, 2, "Garlic Bread", new BigDecimal("100.00"), true));
    }
    public Optional<Restaurant> restaurant(long id) { return Optional.ofNullable(restaurants.get(id)); }
    public Optional<MenuItem> item(long id) { return Optional.ofNullable(items.get(id)); }

    public void printCatalog() {
        System.out.println("\\n--- DEMO RESTAURANTS / MENU ---");
        for (Restaurant r : restaurants.values()) {
            System.out.println("Restaurant ID " + r.id() + ": " + r.name() + " | City: " + r.city());
            for (MenuItem m : items.values()) if (m.restaurantId() == r.id())
                System.out.println("  Item ID " + m.id() + ": " + m.name()
                        + " | Rs. " + m.price() + " | Available: " + m.available());
        }
    }
}
