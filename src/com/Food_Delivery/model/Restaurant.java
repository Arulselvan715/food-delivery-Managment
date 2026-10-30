package com.Food_Delivery.model;

public class Restaurant {

    private int restaurantId;
    private String name;
    private String location;
    private String phone;

    public Restaurant(int restaurantId, String name,
                      String location, String phone) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.location = location;
        this.phone = phone;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void display() {
        System.out.println("Restaurant ID: " + restaurantId);
        System.out.println("Name: " + name);
        System.out.println("Location: " + location);
        System.out.println("Phone: " + phone);
        System.out.println("----------------------");
    }
}
