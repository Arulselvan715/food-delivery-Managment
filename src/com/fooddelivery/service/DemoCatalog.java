package com.fooddelivery.service;

import com.fooddelivery.model.DemoRestaurant;
import com.fooddelivery.model.MenuItem;
import java.math.BigDecimal;
import java.util.*;

/** Demo data for cart and catalog validation. */
public class DemoCatalog {
    private final Map<Long, DemoRestaurant> restaurants = new LinkedHashMap<>();
    private final Map<Long, MenuItem> items = new LinkedHashMap<>();

    public DemoCatalog() {
        restaurants.put(1L, new DemoRestaurant(1, "Pollachi Meals", "Pollachi",
                new BigDecimal("100.00"), true));
        restaurants.put(2L, new DemoRestaurant(2, "Coimbatore Pizza", "Coimbatore",
                new BigDecimal("150.00"), true));
        items.put(101L, new MenuItem(101, 1, "Veg Meals", new BigDecimal("120.00"), true));
        items.put(102L, new MenuItem(102, 1, "Paneer Rice", new BigDecimal("140.00"), true));
        items.put(103L, new MenuItem(103, 1, "Unavailable Sweet", new BigDecimal("50.00"), false));
        items.put(201L, new MenuItem(201, 2, "Margherita Pizza", new BigDecimal("220.00"), true));
        items.put(202L, new MenuItem(202, 2, "Garlic Bread", new BigDecimal("100.00"), true));
    }

    public Optional<DemoRestaurant> restaurant(long id) {
        return Optional.ofNullable(restaurants.get(id));
    }

    public Optional<MenuItem> item(long id) {
        return Optional.ofNullable(items.get(id));
    }

    public void printCatalog() {
        System.out.println("\n--- DEMO RESTAURANTS / MENU ---");
        for (DemoRestaurant r : restaurants.values()) {
            System.out.println("Restaurant ID " + r.id() + ": " + r.name() + " | City: " + r.city());
            for (MenuItem m : items.values()) {
                if (m.restaurantId() == r.id()) {
                    System.out.println("  Item ID " + m.id() + ": " + m.name()
                            + " | Rs. " + m.price() + " | Available: " + m.available());
                }
            }
        }
    }
}
