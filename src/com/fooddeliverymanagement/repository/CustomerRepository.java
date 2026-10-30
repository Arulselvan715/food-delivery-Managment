package com.fooddeliverymanagement.repository;

import com.fooddeliverymanagement.model.Customer;
import java.util.*;

public class CustomerRepository {
    private final Map<Long, Customer> byId = new HashMap<>();
    private final Map<String, Long> phoneIndex = new HashMap<>();

    public void save(Customer customer) {
        byId.put(customer.getId(), customer);
        phoneIndex.put(customer.getPhone(), customer.getId());
    }
    public Optional<Customer> findById(long id) { return Optional.ofNullable(byId.get(id)); }
    public boolean phoneExists(String phone) { return phoneIndex.containsKey(phone); }
}
