package ru.sergeysemenov.webmarketspring.core.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.sergeysemenov.webmarketspring.api.CartDto;


@Component
@RequiredArgsConstructor
public class CartServiceIntegration {
    private final WebClient cartServiceWebClient;

    public CartDto getCart(){
        return cartServiceWebClient.get()
                .uri("/api/v1/cart/get_cart")
                .retrieve()
                .bodyToMono(CartDto.class)
                .block();
    }

    public void clearCart(){
        cartServiceWebClient.get()
                .uri("/api/v1/cart/clear")
                .retrieve()
                .bodyToMono(HttpStatus.class)
                .block();
    }


}
