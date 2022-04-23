package ru.sergeysemenov.webmarketspring.cart.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.sergeysemenov.webmarketspring.api.CartItemDto;
import ru.sergeysemenov.webmarketspring.cart.convertors.CartItemConverter;
import ru.sergeysemenov.webmarketspring.cart.services.CartService;
import ru.sergeysemenov.webmarketspring.cart.utils.Cart;
import ru.sergeysemenov.webmarketspring.cart.utils.CartItem;


import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CartItemConverter cartItemConverter;


    @GetMapping()
    public List<CartItemDto> getProductsInCart(){
        Cart cart = cartService.getCurrentCart();
        return cart.getItems().stream().map(cartItemConverter::entityToDto).collect(Collectors.toList());
    }

    @GetMapping("/add-to-cart/{id}")
    public void addProductIntoCart(@PathVariable Long id){
       cartService.addToCart(id);
    }

    @GetMapping("/increment/{id}")
    public void incrementItemQty(@PathVariable Long id){
        cartService.incrementItemQty(id);
    }

    @GetMapping("/decrement/{id}")
    public void decrementItemQty(@PathVariable Long id){
        cartService.decrementItemQty(id);
    }

    @GetMapping("/clear")
    public void clearCart(){
        System.out.println("clear");
        cartService.clearCart();
    }

}
