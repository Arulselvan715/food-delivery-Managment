package com.fooddeliverymanagement.model;

import java.math.BigDecimal;

public class CartItem {
    private final long menuItemId;
    private String itemName;
    private BigDecimal unitPrice;
    private int quantity;

    public CartItem(long menuItemId, String itemName, BigDecimal unitPrice, int quantity) {
        this.menuItemId = menuItemId; this.itemName = itemName;
        this.unitPrice = unitPrice; this.quantity = quantity;
    }
    public long getMenuItemId() { return menuItemId; }
    public String getItemName() { return itemName; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void refresh(String name, BigDecimal price) { itemName = name; unitPrice = price; }
    public BigDecimal getLineTotal() { return unitPrice.multiply(BigDecimal.valueOf(quantity)); }
}
