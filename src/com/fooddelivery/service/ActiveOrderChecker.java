package com.fooddelivery.service;

/** Module 3 integration contract: return count of orders not Delivered or Cancelled. */
@FunctionalInterface
public interface ActiveOrderChecker {
    int countActiveOrders(long customerId);
}
