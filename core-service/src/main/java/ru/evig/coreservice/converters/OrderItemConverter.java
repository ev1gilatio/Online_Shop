package ru.evig.coreservice.converters;

import org.springframework.stereotype.Component;
import ru.evig.coreservice.dto.OrderItemDto;
import ru.evig.coreservice.entites.OrderItem;

@Component
public class OrderItemConverter {
    public OrderItemDto entityToDto(OrderItem orderItem) {
        return new OrderItemDto(
                orderItem.getProduct().getId(),
                orderItem.getProduct().getTitle(),
                orderItem.getQuantity(),
                orderItem.getPricePerProduct(),
                orderItem.getPrice()
        );
    }
}