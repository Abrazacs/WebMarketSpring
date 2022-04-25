package ru.sergeysemenov.webmarketspring.core.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sergeysemenov.webmarketspring.core.repositories.OrderItemRepository;

@Service
@AllArgsConstructor
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
}
