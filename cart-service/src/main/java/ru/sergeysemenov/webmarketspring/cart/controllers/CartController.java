package ru.sergeysemenov.webmarketspring.cart.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.sergeysemenov.webmarketspring.api.CartDto;
import ru.sergeysemenov.webmarketspring.api.CartItemDto;
import ru.sergeysemenov.webmarketspring.api.StringResponse;
import ru.sergeysemenov.webmarketspring.cart.services.CartService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {
    private final CartService cartService;

    @GetMapping("/generate_id")
    public StringResponse generateGuestCartId() {
        return new StringResponse(UUID.randomUUID().toString());
    }

    @GetMapping("/{guestCartId}")
    public CartDto getCurrentCart(@RequestHeader(required = false) String username, @PathVariable String guestCartId) {
        String currentCartId = selectCartId(username, guestCartId);
        return cartService.getCartDto(currentCartId);
    }

    @GetMapping("/{guestCartId}/add/{productId}")
    public void addProductToCart(@RequestHeader(required = false) String username, @PathVariable String guestCartId, @PathVariable Long productId) {
        String currentCartId = selectCartId(username, guestCartId);
        cartService.addToCart(currentCartId, productId);
    }

    @GetMapping("/{guestCartId}/clear")
    public void clearCurrentCart(@RequestHeader(required = false) String username, @PathVariable String guestCartId) {
        String currentCartId = selectCartId(username, guestCartId);
        cartService.clearCart(currentCartId);
    }

    @GetMapping("/{guestCartId}/items")
    public List<CartItemDto> getProductsInCart(@RequestHeader(required = false) String username, @PathVariable String guestCartId){
        String currentCartId = selectCartId(username, guestCartId);
        return cartService.getListOfCartItemsDto(currentCartId);
    }


    @GetMapping("/{guestCartId}/increment/{id}")
    public void incrementItemQty(@RequestHeader(required = false) String username, @PathVariable String guestCartId, @PathVariable Long id){
        String currentCartId = selectCartId(username, guestCartId);
        cartService.incrementItemQty(id, currentCartId);
    }

    @GetMapping("/{guestCartId}/decrement/{id}")
    public void decrementItemQty(@RequestHeader(required = false) String username, @PathVariable String guestCartId, @PathVariable Long id){
        String currentCartId = selectCartId(username, guestCartId);
        cartService.decrementItemQty(id, currentCartId);
    }


    private String selectCartId(String username, String guestCartId) {
        if (username != null) {
            return username;
        }
        return guestCartId;
    }

}
