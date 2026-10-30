package com.fooddelivery.model;

import java.math.BigDecimal;

/** Demo/reference model for catalog and cart validation. */
public record DemoRestaurant(long id, String name, String city,
                             BigDecimal minimumOrderValue, boolean openAndWithinHours) {}
