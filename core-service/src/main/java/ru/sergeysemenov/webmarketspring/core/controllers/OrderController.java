package ru.sergeysemenov.webmarketspring.core.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(
            summary = "Запрос на получение списка заказов пользователя",
            responses = {
                    @ApiResponse(
                            description = "Возвращает перечень заказзов залогиневшегося пользователья",
                            responseCode = "200"
                    )

            }
    )
    @GetMapping
    public List<Order> getUsersOrders (@RequestHeader @Parameter(description = "Имя пользователя", required = true) String username){
        return orderService.findUserOrders(username);
    }

    @Operation(
            summary = "Запрос создание нового заказа",
            responses = {
                    @ApiResponse(
                            description = "Заказ создан",
                            responseCode = "201"
                    )

            }
    )

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestHeader String username, @RequestBody StringDto response){
        orderService.createOrder(username, response.getValue());
    }

}
