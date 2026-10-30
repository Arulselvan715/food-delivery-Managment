package com.fooddelivery.service;

import com.fooddelivery.model.Address;
import com.fooddelivery.model.Customer;
import com.fooddelivery.repository.CustomerRepository;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;

public class CustomerService {
    private static final Pattern PHONE = Pattern.compile("[0-9]{10}");
    private static final Pattern EMAIL = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private final CustomerRepository repository;
    private final AtomicLong customerIds = new AtomicLong(1000);
    private final AtomicLong addressIds = new AtomicLong(5000);

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer register(String name, String phone, String email, List<Address> addresses) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name is required.");
        }
        phone = phone == null ? "" : phone.trim();
        email = email == null ? "" : email.trim();
        if (!PHONE.matcher(phone).matches()) {
            throw new IllegalArgumentException("Phone must be 10 digits.");
        }
        if (repository.phoneExists(phone)) {
            throw new IllegalArgumentException("Phone number is already registered.");
        }
        if (!EMAIL.matcher(email).matches()) {
            throw new IllegalArgumentException("Enter a valid email.");
        }
        if (addresses == null || addresses.isEmpty()) {
            throw new IllegalArgumentException("At least one delivery address is required.");
        }

        for (Address address : addresses) {
            address.validate();
            address.setId(addressIds.incrementAndGet());
        }
        Customer customer = new Customer(customerIds.incrementAndGet(), name.trim(), phone, email, addresses);
        repository.save(customer);
        return customer;
    }

    public Customer getCustomer(long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Customer not found."));
    }

    public Address addAddress(long customerId, Address address) {
        address.validate();
        address.setId(addressIds.incrementAndGet());
        getCustomer(customerId).addAddress(address);
        return address;
    }

    public void removeAddress(long customerId, long addressId) {
        Customer customer = getCustomer(customerId);
        if (customer.getAddresses().size() <= 1) {
            throw new IllegalArgumentException("Customer must retain at least one delivery address.");
        }
        if (!customer.removeAddress(addressId)) {
            throw new IllegalArgumentException("Address not found for this customer.");
        }
    }
}
