package com.github.tachesimazzoca.java.examples.jbehave;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cart {
    private Map<String, CartItem> cartItemMap;

    public Cart() {
        cartItemMap = new ConcurrentHashMap<String, CartItem>();
    }

    public void addCartItem(CartItem cartItem) {
        cartItemMap.put(cartItem.getItem().getCode(), cartItem);
    }

    public void removeCartItem(String code) {
        cartItemMap.remove(code);
    }

    public int calculateTotal() {
        int total = 0;
        for (Map.Entry<String, CartItem> entry : cartItemMap.entrySet()) {
            total += entry.getValue().calculateSubTotal();
        }
        return total;
    }
}
