package com.fooddelivery.model;

public class Address {
    private long id;
    private final String label, houseOrFlat, street, city, state, pincode;

    public Address(long id, String label, String houseOrFlat, String street,
                   String city, String state, String pincode) {
        this.id = id;
        this.label = label;
        this.houseOrFlat = houseOrFlat;
        this.street = street;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public String getHouseOrFlat() {
        return houseOrFlat;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPincode() {
        return pincode;
    }

    public void validate() {
        if (blank(label) || blank(houseOrFlat) || blank(street) || blank(city)
                || blank(state) || blank(pincode)) {
            throw new IllegalArgumentException("All address fields are required.");
        }
        if (!pincode.matches("\\d{6}")) {
            throw new IllegalArgumentException("Pincode must contain exactly 6 digits.");
        }
    }

    private boolean blank(String s) {
        return s == null || s.isBlank();
    }

    @Override
    public String toString() {
        return "Address ID: " + id + " | " + label + " | " + houseOrFlat + ", "
                + street + ", " + city + ", " + state + " - " + pincode;
    }
}
