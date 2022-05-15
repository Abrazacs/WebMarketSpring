package ru.sergeysemenov.webmarketspring.core.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sergeysemenov.webmarketspring.api.CartDto;
import ru.sergeysemenov.webmarketspring.api.CartItemDto;
import ru.sergeysemenov.webmarketspring.core.entities.Order;
import ru.sergeysemenov.webmarketspring.core.entities.OrderItem;
import ru.sergeysemenov.webmarketspring.core.exceptions.ResourceNotFoundException;
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
    private final ProductService productService;

    @Transactional
    public void createOrder(String username, String address) {
        CartDto cartDto = cartServiceIntegration.getCart(username);
        if(cartDto.getTotalPrice().equals(BigDecimal.ZERO)){
            throw new IllegalStateException("Нельзя оформить заказ для пустой корзины");
        }
        Order order = convertCartToOrder(cartDto, username);

        log.info(order.toString());
        orderRepository.save(order);
        cartServiceIntegration.clearCart(username);
    }

    private Order convertCartToOrder(CartDto cartDto, String username) {
        Order order = new Order();
        List<OrderItem> orderItems = cartDto.getItems().stream().map((CartItemDto itemDto) -> convertCartItemToOrderItem(itemDto, order)).toList();
        order.setOrderItemsList(orderItems);
        order.setTotalPrice(cartDto.getTotalPrice());
        order.setUsername(username);
        return order;
    }

    private OrderItem convertCartItemToOrderItem(CartItemDto itemDto, Order order) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(productService.findById(itemDto.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found")));
        orderItem.setPricePerProduct(itemDto.getPricePerProduct());
        orderItem.setQuantity(itemDto.getQuantity());
        orderItem.setPrice(itemDto.getPrice());
        return orderItem;
    }

    public List<Order> findUserOrders(String username) {
        return orderRepository.findAllByUsername(username);
    }

}
