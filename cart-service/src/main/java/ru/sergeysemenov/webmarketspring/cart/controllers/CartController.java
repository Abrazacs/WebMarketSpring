package ru.sergeysemenov.webmarketspring.cart.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.sergeysemenov.webmarketspring.api.CartDto;
import ru.sergeysemenov.webmarketspring.api.CartItemDto;
import ru.sergeysemenov.webmarketspring.cart.services.CartService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {
    private final CartService cartService;

    @GetMapping("/items")
    public List<CartItemDto> getProductsInCart(){
        return cartService.getListOfCartItemsDto();
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
        cartService.clearCart();
        log.info("Cart cleared");
    }

    @GetMapping()
    public CartDto getCart(){
        return cartService.getCartDto();
    }

}
