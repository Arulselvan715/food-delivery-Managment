package com.fooddeliverymanagement.model;

import java.math.BigDecimal;

/** Demo/reference model only. Use Module 1's Restaurant entity during team integration. */
public record Restaurant(long id, String name, String city,
                         BigDecimal minimumOrderValue, boolean openAndWithinHours) {}
