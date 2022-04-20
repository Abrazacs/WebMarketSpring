package ru.sergeysemenov.webmarketspring.cart.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.sergeysemenov.webmarketspring.cart.services.CartService;
import ru.sergeysemenov.webmarketspring.cart.utils.Cart;
import ru.sergeysemenov.webmarketspring.cart.utils.CartItem;


import java.util.List;


@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;


    @GetMapping
    public List<CartItem> getProductsInCart(){
        Cart cart = cartService.getCurrentCart();
        return cart.getItems();
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
