package ru.sergeysemenov.webmarketspring.utils;


import lombok.Getter;
import lombok.Setter;

import org.springframework.stereotype.Component;
import ru.sergeysemenov.webmarketspring.entities.Product;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class Cart {
    private List<CartItem> items;
    private BigDecimal totalPrice;

    public void add(Product p) {
        if(!items.isEmpty()) {
            for (CartItem item : items) {
                if (item.getProductId().equals(p.getId())) {
                    item.incrementQuantity();
                    recalculate();
                    return;
                }
            }
        }
        CartItem cartItem = new CartItem(p.getId(), p.getTitle(), 1, p.getPrice(), p.getPrice());
        items.add(cartItem);
        recalculate();
    }

    public void clear() {
        items.clear();
        totalPrice = BigDecimal.ZERO;
    }

    public void recalculate() {
        totalPrice = BigDecimal.ZERO;
        items.forEach(i -> totalPrice = totalPrice.add(i.getPrice()));
    }

}