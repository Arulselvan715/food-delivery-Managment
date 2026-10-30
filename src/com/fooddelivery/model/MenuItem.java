package com.fooddelivery.model;

import java.math.BigDecimal;

public record MenuItem(long id, long restaurantId, String name,
                       BigDecimal price, boolean available) {}
