package ru.sergeysemenov.webmarketspring.core.converters;

import org.springframework.stereotype.Component;
import ru.sergeysemenov.webmarketspring.api.OrderItemDto;
import ru.sergeysemenov.webmarketspring.core.entities.OrderItem;

@Component
public class OrderItemConverter {
    public OrderItemDto entityToDto(OrderItem o) {
        return new OrderItemDto(o.getProduct().getId(), o.getProduct().getTitle(), o.getQuantity(), o.getPricePerProduct(), o.getPrice());
    }
}
