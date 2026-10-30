package com.fooddelivery.service;

import com.fooddelivery.model.Address;
import com.fooddelivery.model.Cart;
import com.fooddelivery.model.CartItem;
import com.fooddelivery.model.Customer;
import com.fooddelivery.model.DemoRestaurant;
import com.fooddelivery.model.MenuItem;
import com.fooddelivery.repository.CartRepository;
import com.fooddelivery.repository.CustomerRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CartService {
    private static final int MAX_ITEM_QUANTITY = 10;
    private final CartRepository carts;
    private final CustomerRepository customers;
    private final DemoCatalog catalog;
    private final ActiveOrderChecker orderChecker;

    public CartService(CartRepository carts, CustomerRepository customers,
                       DemoCatalog catalog, ActiveOrderChecker orderChecker) {
        this.carts = carts;
        this.customers = customers;
        this.catalog = catalog;
        this.orderChecker = orderChecker;
    }

    public void addItem(long customerId, long restaurantId, long menuItemId, int quantity) {
        requireCustomer(customerId);
        checkQuantity(quantity);
        DemoRestaurant restaurant = getRestaurant(restaurantId);
        MenuItem menuItem = getMenuItem(menuItemId);
        if (menuItem.restaurantId() != restaurantId) {
            throw new IllegalArgumentException("Selected item does not belong to this restaurant.");
        }
        if (!restaurant.openAndWithinHours()) {
            throw new IllegalArgumentException("Restaurant is not open for orders.");
        }
        if (!menuItem.available()) {
            throw new IllegalArgumentException("Menu item is unavailable.");
        }

        Cart cart = carts.getOrCreate(customerId);
        if (cart.getRestaurantId() != null && cart.getRestaurantId() != restaurantId) {
            throw new IllegalArgumentException("Cart already has items from another restaurant. Clear it first.");
        }

        CartItem old = cart.getItem(menuItemId);
        int totalQuantity = quantity + (old == null ? 0 : old.getQuantity());
        checkQuantity(totalQuantity);
        cart.setRestaurantId(restaurantId);
        if (old == null) {
            cart.addOrReplace(new CartItem(menuItemId, menuItem.name(), menuItem.price(), quantity));
        } else {
            old.setQuantity(totalQuantity);
            old.refresh(menuItem.name(), menuItem.price());
        }
    }

    public String viewCart(long customerId) {
        requireCustomer(customerId);
        Cart cart = carts.getOrCreate(customerId);
        if (cart.isEmpty()) {
            return "Cart is empty.";
        }
        DemoRestaurant restaurant = getRestaurant(cart.getRestaurantId());
        StringBuilder out = new StringBuilder("\nRestaurant: " + restaurant.name() + "\n");
        out.append(String.format("%-22s %-10s %-8s %-10s%n", "Item", "Price", "Qty", "Total"));
        for (CartItem item : cart.getItems()) {
            out.append(String.format("%-22s %-10s %-8d %-10s%n", item.getItemName(),
                    item.getUnitPrice(), item.getQuantity(), item.getLineTotal()));
        }
        out.append("Items subtotal: Rs. ").append(cart.subtotal());
        return out.toString();
    }

    public void updateQuantity(long customerId, long itemId, int quantity) {
        requireCustomer(customerId);
        checkQuantity(quantity);
        Cart cart = carts.getOrCreate(customerId);
        CartItem item = cart.getItem(itemId);
        if (item == null) {
            throw new IllegalArgumentException("Item is not in the cart.");
        }
        MenuItem latest = getMenuItem(itemId);
        if (!latest.available()) {
            throw new IllegalArgumentException("Item is unavailable.");
        }
        item.setQuantity(quantity);
        item.refresh(latest.name(), latest.price());
    }

    public void removeItem(long customerId, long itemId) {
        requireCustomer(customerId);
        Cart cart = carts.getOrCreate(customerId);
        if (cart.remove(itemId) == null) {
            throw new IllegalArgumentException("Item is not in the cart.");
        }
        if (cart.isEmpty()) {
            cart.setRestaurantId(null);
        }
    }

    public void clearCart(long customerId) {
        requireCustomer(customerId);
        carts.getOrCreate(customerId).clear();
    }

    public CheckoutResult validateCheckout(long customerId, long addressId) {
        Customer customer = requireCustomer(customerId);
        Cart cart = carts.getOrCreate(customerId);
        List<String> notes = new ArrayList<>();
        if (cart.isEmpty()) {
            return CheckoutResult.fail("Cart is empty.", notes);
        }

        Address address = customer.getAddress(addressId);
        if (address == null) {
            return CheckoutResult.fail("Address does not belong to this customer.", notes);
        }

        DemoRestaurant restaurant = getRestaurant(cart.getRestaurantId());
        if (!restaurant.openAndWithinHours()) {
            return CheckoutResult.fail("Restaurant is not currently accepting orders.", notes);
        }

        // Re-check availability and refresh current menu prices.
        for (CartItem ci : new ArrayList<>(cart.getItems())) {
            var found = catalog.item(ci.getMenuItemId());
            if (found.isEmpty() || !found.get().available()) {
                cart.remove(ci.getMenuItemId());
                notes.add(ci.getItemName() + " became unavailable and was removed.");
            } else {
                MenuItem current = found.get();
                ci.refresh(current.name(), current.price());
            }
        }
        if (cart.isEmpty()) {
            cart.setRestaurantId(null);
            return CheckoutResult.fail("All items became unavailable. Add other items.", notes);
        }
        if (!address.getCity().equalsIgnoreCase(restaurant.city())) {
            return CheckoutResult.fail("Delivery address must be in " + restaurant.city() + ".", notes);
        }
        if (orderChecker != null && orderChecker.countActiveOrders(customerId) >= 3) {
            return CheckoutResult.fail("Customer already has 3 active orders.", notes);
        }

        BigDecimal subtotal = cart.subtotal();
        if (subtotal.compareTo(restaurant.minimumOrderValue()) < 0) {
            return CheckoutResult.fail("Minimum order value is Rs. " + restaurant.minimumOrderValue(), notes);
        }

        return CheckoutResult.ok(subtotal, notes);
    }

    private Customer requireCustomer(long id) {
        return customers.findById(id).orElseThrow(() -> new IllegalArgumentException("Customer not found."));
    }

    private DemoRestaurant getRestaurant(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Cart has no restaurant selected.");
        }
        return catalog.restaurant(id).orElseThrow(() -> new IllegalArgumentException("Restaurant not found."));
    }

    private DemoRestaurant getRestaurant(long id) {
        return catalog.restaurant(id).orElseThrow(() -> new IllegalArgumentException("Restaurant not found."));
    }

    private MenuItem getMenuItem(long id) {
        return catalog.item(id).orElseThrow(() -> new IllegalArgumentException("Menu item not found."));
    }

    private void checkQuantity(int quantity) {
        if (quantity < 1 || quantity > MAX_ITEM_QUANTITY) {
            throw new IllegalArgumentException("Quantity must be from 1 to 10.");
        }
    }

    public record CheckoutResult(boolean valid, String message,
                                 BigDecimal itemsSubtotal, List<String> notifications) {
        static CheckoutResult ok(BigDecimal subtotal, List<String> notes) {
            return new CheckoutResult(true, "OK", subtotal, List.copyOf(notes));
        }

        static CheckoutResult fail(String message, List<String> notes) {
            return new CheckoutResult(false, message, BigDecimal.ZERO, List.copyOf(notes));
        }
    }
}
