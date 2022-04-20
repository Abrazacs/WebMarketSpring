package ru.sergeysemenov.webmarketspring.cart.convertors;

import org.springframework.stereotype.Component;
import ru.sergeysemenov.webmarketspring.api.CartItemDto;
import ru.sergeysemenov.webmarketspring.cart.utils.CartItem;

@Component
public class CartItemConverter {
    public CartItemDto entityToDto(CartItem item){
        return new CartItemDto(
                item.getProductId(),
                item.getProductTitle(),
                item.getQuantity(),
                item.getPricePerProduct(),
                item.getPrice()
        );
    }
}
