package com.fooddeliverymanagement.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Customer {
    private final long id;
    private final String name, phone, email;
    private final List<Address> addresses = new ArrayList<>();

    public Customer(long id, String name, String phone, String email, List<Address> addresses) {
        this.id = id; this.name = name; this.phone = phone; this.email = email;
        this.addresses.addAll(addresses);
    }
    public long getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public List<Address> getAddresses() { return Collections.unmodifiableList(addresses); }
    public Address getAddress(long addressId) {
        return addresses.stream().filter(a -> a.getId() == addressId).findFirst().orElse(null);
    }
    public void addAddress(Address address) { addresses.add(address); }
    public boolean removeAddress(long addressId) {
        return addresses.removeIf(a -> a.getId() == addressId);
    }
}
