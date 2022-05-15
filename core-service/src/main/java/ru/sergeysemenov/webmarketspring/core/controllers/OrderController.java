package ru.sergeysemenov.webmarketspring.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.sergeysemenov.webmarketspring.api.StringDto;
import ru.sergeysemenov.webmarketspring.core.entities.Order;
import ru.sergeysemenov.webmarketspring.core.services.OrderService;

import java.util.List;


@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public List<Order> getUsersOrders (@RequestHeader String username){
        return orderService.findUserOrders(username);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestHeader String username, @RequestBody StringDto response){
        orderService.createOrder(username, response.getValue());
    }

}
