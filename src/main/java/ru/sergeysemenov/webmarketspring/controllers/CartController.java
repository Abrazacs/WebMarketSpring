package ru.sergeysemenov.webmarketspring.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.sergeysemenov.webmarketspring.converters.ProductConverter;
import ru.sergeysemenov.webmarketspring.dtos.ProductDto;
import ru.sergeysemenov.webmarketspring.utils.Cart;
import ru.sergeysemenov.webmarketspring.services.ProductService;

import java.util.List;


@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final Cart cart;
    private final ProductService productService;
    private final ProductConverter productConverter;


    @GetMapping
    public List<ProductDto> getProductsInCart(){
        return cart.getProductsList();
    }

    @GetMapping("/add-to-cart/{id}")
    public void addProductIntoCart(@PathVariable Long id){
        ProductDto dto = productConverter.entityToDto(productService.findProductById(id));
        cart.getProductsList().add(dto);
    }
}
