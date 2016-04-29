package com.github.tachesimazzoca.java.examples.jbehave;

public class CartItem {
    private final Item item;
    private final int quantity;

    public CartItem(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public int calculateSubTotal() {
        return item.getPrice() * quantity;
    }
}
