package ru.sergeysemenov.webmarketspring.cart.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;
import ru.sergeysemenov.webmarketspring.api.ProductDto;
import ru.sergeysemenov.webmarketspring.cart.convertors.CartConverter;
import ru.sergeysemenov.webmarketspring.cart.convertors.CartItemConverter;
import ru.sergeysemenov.webmarketspring.cart.integrations.ProductServiceIntegration;
import ru.sergeysemenov.webmarketspring.cart.services.CartService;
import ru.sergeysemenov.webmarketspring.cart.utils.Cart;

import java.math.BigDecimal;

@SpringBootTest(classes = CartService.class)
public class CartServiceTests {

    @Autowired
    private CartService cartService;

    @MockBean
    private ProductServiceIntegration productServiceIntegration;

    @MockBean
    private CartConverter cartConverter;

    @MockBean
    private CartItemConverter cartItemConverter;

    @Test
    public void addItemIntoCart(){
        ProductDto productDto = new ProductDto(1L,"Orange", BigDecimal.valueOf(10),"Food");
        Mockito.doReturn(productDto)
                .when(productServiceIntegration)
                .findById(1L);
        cartService.addToCart(1L);
        Cart cart = cartService.getCurrentCart();
        Assert.notEmpty(cart.getItems(),"cart is empty");
        Assert.isTrue(cart.getTotalPrice().equals(BigDecimal.valueOf(10)),cart.getTotalPrice().toString());
    }

    @Test
    public void itemIncrementTest(){
        cartService.incrementItemQty(1L);
        Cart cart = cartService.getCurrentCart();
        Assert.isTrue(cart.getTotalPrice().equals(BigDecimal.valueOf(20)),cart.getTotalPrice().toString());
    }






}
