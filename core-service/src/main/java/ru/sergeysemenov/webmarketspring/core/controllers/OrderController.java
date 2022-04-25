package ru.sergeysemenov.webmarketspring.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.sergeysemenov.webmarketspring.core.services.OrderService;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/create")
    public void createOrder(@RequestHeader String username){
        orderService.createOrder(username);
    }

}
