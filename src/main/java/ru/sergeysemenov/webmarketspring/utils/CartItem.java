package ru.sergeysemenov.webmarketspring.utils;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private Long productId;
    private String productTitle;
    private int quantity;
    private BigDecimal pricePerProduct;
    private BigDecimal price;

    public void incrementQuantity() {
        quantity++;
        price = price.add(pricePerProduct);
    }

    public void decrementQuantity(){
        quantity--;
        price = price.subtract(pricePerProduct);
    }
}
