package ru.sergeysemenov.webmarketspring.cart.convertors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sergeysemenov.webmarketspring.api.CartDto;
import ru.sergeysemenov.webmarketspring.cart.utils.Cart;

import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class CartConverter {
    private final CartItemConverter converter;

    public CartDto entityToDto(Cart cart){
        return new CartDto(cart.getItems().stream().map(converter::entityToDto).collect(Collectors.toList()), cart.getTotalPrice());
    }
}
