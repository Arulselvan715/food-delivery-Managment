package com.fooddeliverymanagement.model;

import java.math.BigDecimal;

/** Demo/reference model only. Use Module 1's MenuItem entity during team integration. */
public record MenuItem(long id, long restaurantId, String name,
                       BigDecimal price, boolean available) {}
