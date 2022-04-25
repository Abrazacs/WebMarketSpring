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
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productServiceIntegration;
    private final CartConverter cartConverter;
    private final CartItemConverter cartItemConverter;
    private Cart cart;

    @PostConstruct
    public void init() {
        cart = new Cart();
        cart.setItems(new ArrayList<>());
    }

    public Cart getCurrentCart() {
        return cart;
    }

    public void addToCart(Long productId) {
        ProductDto p = productServiceIntegration.findById(productId);
        cart.add(p);
    }

    public void clearCart(){
        cart.clear();
    }

    public void incrementItemQty(Long id){
        cart.getItems().forEach(cartItem -> {
            if(cartItem.getProductId() == id){
                cartItem.incrementQuantity();
                cart.recalculate();
                return;
            }
        });
    }

    public void decrementItemQty(Long id){
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

    public CartDto getCartDto(){
        return cartConverter.entityToDto(cart);
    }

    public List<CartItemDto> getListOfCartItemsDto(){
        return cart.getItems().stream().map(cartItemConverter::entityToDto).collect(Collectors.toList());
    }

}
