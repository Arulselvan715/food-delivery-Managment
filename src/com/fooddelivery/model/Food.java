package com.fooddelivery.model;

public class Food {

    private int foodId;
    private int restaurantId;
    private String foodName;
    private double price;
    private String category;
    private boolean available;

    public Food(int foodId, int restaurantId, String foodName,
                double price, String category, boolean available) {

        this.foodId = foodId;
        this.restaurantId = restaurantId;
        this.foodName = foodName;
        this.price = price;
        this.category = category;
        this.available = available;
    }

    public int getFoodId() {
        return foodId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public String getFoodName() {
        return foodName;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void display() {
        System.out.println("Food ID: " + foodId);
        System.out.println("Restaurant ID: " + restaurantId);
        System.out.println("Food Name: " + foodName);
        System.out.println("Price: Rs." + price);
        System.out.println("Category: " + category);
        System.out.println("Available: " + (available ? "Yes" : "No"));
        System.out.println("----------------------");
    }
}
