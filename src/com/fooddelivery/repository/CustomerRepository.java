package com.fooddelivery.repository;

import com.fooddelivery.model.Customer;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CustomerRepository {
    private final Map<Long, Customer> byId = new HashMap<>();
    private final Map<String, Long> phoneIndex = new HashMap<>();

    public void save(Customer customer) {
        byId.put(customer.getId(), customer);
        phoneIndex.put(customer.getPhone(), customer.getId());
    }

    public Optional<Customer> findById(long id) {
        return Optional.ofNullable(byId.get(id));
    }

    public boolean phoneExists(String phone) {
        return phoneIndex.containsKey(phone);
    }
}
