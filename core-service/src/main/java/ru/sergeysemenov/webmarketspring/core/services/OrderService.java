package ru.sergeysemenov.webmarketspring.core.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sergeysemenov.webmarketspring.api.CartDto;
import ru.sergeysemenov.webmarketspring.api.CartItemDto;
import ru.sergeysemenov.webmarketspring.core.entities.Order;
import ru.sergeysemenov.webmarketspring.core.entities.OrderItem;
import ru.sergeysemenov.webmarketspring.core.integrations.CartServiceIntegration;
import ru.sergeysemenov.webmarketspring.core.repositories.OrderRepository;

import java.math.BigDecimal;
import java.util.List;


@Service
@AllArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartServiceIntegration cartServiceIntegration;
    private final UserService userService;

    @Transactional
    public void createOrder(String username) {
        CartDto cartDto = cartServiceIntegration.getCart();
        if(cartDto.getTotalPrice().equals(BigDecimal.ZERO)){
            return;
        }
        Order order = convertCartToOrder(cartDto, username);
        log.info(order.toString());
        orderRepository.save(order);
        cartServiceIntegration.clearCart();
    }

    public Order convertCartToOrder(CartDto cartDto, String username) {
        Order order = new Order();
        List<OrderItem> orderItems = cartDto.getItems().stream().map(this::convertCartItemToOrderItem).toList();
        order.setOrderItemsList(orderItems);
        order.setTotalPrice(cartDto.getTotalPrice());
        order.setUser(userService.findByUsername(username).get());
        return order;
    }

    public OrderItem convertCartItemToOrderItem(CartItemDto itemDto) {
        OrderItem orderItem = new OrderItem();
        orderItem.setProductId(itemDto.getProductId());
        orderItem.setPricePerProduct(itemDto.getPricePerProduct());
        orderItem.setQuantity(itemDto.getQuantity());
        orderItem.setPrice(itemDto.getPrice());
        return orderItem;
    }
}
