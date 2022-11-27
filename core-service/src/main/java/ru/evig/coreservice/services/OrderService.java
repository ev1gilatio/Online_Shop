package ru.evig.coreservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.evig.coreservice.dto.Cart;
import ru.evig.coreservice.dto.OrderDetailsDto;
import ru.evig.coreservice.entites.Order;
import ru.evig.coreservice.entites.OrderItem;
import ru.evig.coreservice.exceptions.ResourceNotFoundException;
import ru.evig.coreservice.repositories.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CartService cartService;
    private final ProductsService productsService;
    private final OrderRepository orderRepository;

    @Transactional
    public void createOrder(String username, OrderDetailsDto orderDetailsDto, String cartName) {
        Cart currentCart = cartService.getCurrentCart(cartName);

        Order order = new Order();
        order.setAddress(orderDetailsDto.getAddress());
        order.setPhone(orderDetailsDto.getPhone());
        order.setUsername(username);
        order.setTotalPrice(currentCart.getTotalPrice());
        List<OrderItem> items = currentCart.getItems().stream()
                .map(o -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order);
                    orderItem.setQuantity(o.getQuantity());
                    orderItem.setPricePerProduct(o.getPricePerProduct());
                    orderItem.setPrice(o.getPricePerProduct());
                    orderItem.setProduct(productsService.findById(o.getProductId()).orElseThrow(() ->
                            new ResourceNotFoundException("Product not found")));

                    return orderItem;
                }).collect(Collectors.toList());
        order.setItems(items);

        orderRepository.save(order);
        cartService.clearCart(cartName);
    }

    public List<Order> findOrdersByUserName(String username) {
        try {
            return orderRepository.findByUsername(username);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}