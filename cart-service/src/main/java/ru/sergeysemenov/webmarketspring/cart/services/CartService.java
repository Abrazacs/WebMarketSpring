package ru.sergeysemenov.webmarketspring.cart.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
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
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productServiceIntegration;
    private final CartConverter cartConverter;
    private final CartItemConverter cartItemConverter;
    private final RedisTemplate<String, Object> redisTemplate;


    public Cart getCurrentCart(String cartId) {
        if (!redisTemplate.hasKey(cartId)) {
            Cart cart = new Cart();
            redisTemplate.opsForValue().set(cartId,cart);
        }
        return (Cart) redisTemplate.opsForValue().get(cartId);
    }

    public void addToCart(String cartId, Long productId) {
        execute(cartId,cart -> {
            ProductDto p = productServiceIntegration.findById(productId);cart.add(p);
        });
    }

    public void clearCart(String cartId){
        execute(cartId, Cart::clear);
    }

    public void incrementItemQty(Long id, String cartId){
        execute(cartId, cart -> cart.getItems().forEach(cartItem -> {
            if (cartItem.getProductId() == id) {
                cartItem.incrementQuantity();
                cart.recalculate();
                return;
            }
        }));
    }

    public void decrementItemQty(Long id, String cartId){
        execute(cartId,cart -> {
            for (CartItem item:cart.getItems()) {
            if(item.getProductId() == id){
                item.decrementQuantity();
                if(item.getQuantity() == 0){
                    cart.getItems().remove(item);
                }
                cart.recalculate();
                return;
            }
        }});
    }

    public CartDto getCartDto(String cartId){
        Cart cart = getCurrentCart(cartId);
        return cartConverter.entityToDto(cart);
    }

    public List<CartItemDto> getListOfCartItemsDto(String cartId){
        Cart cart = getCurrentCart(cartId);
        return cart.getItems().stream().map(cartItemConverter::entityToDto).collect(Collectors.toList());
    }

    private void execute(String cartId, Consumer<Cart> action) {
        Cart cart = getCurrentCart(cartId);
        action.accept(cart);
        redisTemplate.opsForValue().set(cartId, cart);
    }

    public void mergeCarts (String username, String guestCartId){
        Cart tempCart = getCurrentCart(guestCartId);
        execute(username, cart -> cart.setItems(tempCart.getItems()));
        execute(username, cart -> cart.setTotalPrice(tempCart.getTotalPrice()));
        redisTemplate.delete(guestCartId);
    }

    public boolean isCartExist (String guestCartId){
        return redisTemplate.hasKey(guestCartId);
    }

}
