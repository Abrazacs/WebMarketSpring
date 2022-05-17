package ru.sergeysemenov.webmarketspring.core.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sergeysemenov.webmarketspring.api.OrderDto;
import ru.sergeysemenov.webmarketspring.core.entities.Order;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderConverter {
    private final OrderItemConverter orderItemConverter;

    public OrderDto entityToDto(Order o) {
        return new OrderDto(o.getId(), o.getOrderItemsList().stream().map(orderItemConverter::entityToDto).collect(Collectors.toList()), o.getTotalPrice());
    }

}
