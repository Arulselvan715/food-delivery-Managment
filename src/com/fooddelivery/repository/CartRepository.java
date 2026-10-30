package com.fooddelivery.repository;

import com.fooddelivery.model.Cart;
import java.util.HashMap;
import java.util.Map;

public class CartRepository {
    private final Map<Long, Cart> carts = new HashMap<>();

    public Cart getOrCreate(long customerId) {
        return carts.computeIfAbsent(customerId, Cart::new);
    }
}
