package com.fooddeliverymanagement.model;

import java.math.BigDecimal;
import java.util.*;

public class Cart {
    private final long customerId;
    private Long restaurantId;
    private final Map<Long, CartItem> items = new LinkedHashMap<>();

    public Cart(long customerId) { this.customerId = customerId; }
    public long getCustomerId() { return customerId; }
    public Long getRestaurantId() { return restaurantId; }
    public void setRestaurantId(Long restaurantId) { this.restaurantId = restaurantId; }
    public Collection<CartItem> getItems() { return Collections.unmodifiableCollection(items.values()); }
    public CartItem getItem(long itemId) { return items.get(itemId); }
    public void addOrReplace(CartItem item) { items.put(item.getMenuItemId(), item); }
    public CartItem remove(long itemId) { return items.remove(itemId); }
    public boolean isEmpty() { return items.isEmpty(); }
    public void clear() { items.clear(); restaurantId = null; }
    public BigDecimal subtotal() {
        return items.values().stream().map(CartItem::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
