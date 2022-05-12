package ru.sergeysemenov.webmarketspring.cart.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sergeysemenov.webmarketspring.api.CartDto;
import ru.sergeysemenov.webmarketspring.api.CartItemDto;
import ru.sergeysemenov.webmarketspring.api.ProductDto;
import ru.sergeysemenov.webmarketspring.cart.convertors.CartConverter;
import ru.sergeysemenov.webmarketspring.cart.convertors.CartItemConverter;
import ru.sergeysemenov.webmarketspring.cart.integrations.ProductServiceIntegration;
import ru.sergeysemenov.webmarketspring.cart.utils.Cart;
import ru.sergeysemenov.webmarketspring.cart.utils.CartItem;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productServiceIntegration;
    private final CartConverter cartConverter;
    private final CartItemConverter cartItemConverter;
    private Map<String, Cart> carts;

    @PostConstruct
    public void init() {
        carts = new HashMap<>();
    }

    public Cart getCurrentCart(String cartId) {
        if (!carts.containsKey(cartId)) {
            Cart cart = new Cart();
            carts.put(cartId, cart);
        }
        return carts.get(cartId);
    }

    public void addToCart(String cartId, Long productId) {
        ProductDto p = productServiceIntegration.findById(productId);
        getCurrentCart(cartId).add(p);
    }

    public void clearCart(String cartId){
        getCurrentCart(cartId).clear();
    }

    public void incrementItemQty(Long id, String cartId){
        Cart cart = getCurrentCart(cartId);
        cart.getItems().forEach(cartItem -> {
            if(cartItem.getProductId() == id){
                cartItem.incrementQuantity();
                cart.recalculate();
                return;
            }
        });
    }

    public void decrementItemQty(Long id, String cartId){
        Cart cart = getCurrentCart(cartId);
        for (CartItem item:cart.getItems()) {
            if(item.getProductId() == id){
                item.decrementQuantity();
                if(item.getQuantity() == 0){
                    cart.getItems().remove(item);
                }
                cart.recalculate();
                return;
            }
        }
    }

    public CartDto getCartDto(String cartId){
        Cart cart = getCurrentCart(cartId);
        return cartConverter.entityToDto(cart);
    }

    public List<CartItemDto> getListOfCartItemsDto(String cartId){
        Cart cart = getCurrentCart(cartId);
        return cart.getItems().stream().map(cartItemConverter::entityToDto).collect(Collectors.toList());
    }

}
