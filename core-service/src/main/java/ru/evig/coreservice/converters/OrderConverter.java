package ru.evig.coreservice.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.evig.coreservice.dto.OrderDto;
import ru.evig.coreservice.entites.Order;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderConverter {
    private final OrderItemConverter orderItemConverter;

    public Order dtoToEntity(OrderDto orderDto) {
        throw new UnsupportedOperationException();
    }

    public OrderDto entityToDto(Order order) {
        OrderDto out = new OrderDto();
        out.setId(order.getId());
        out.setAddress(order.getAddress());
        out.setPhone(order.getPhone());
        out.setTotalPrice(order.getTotalPrice());
        out.setUsername(order.getUsername());
        out.setItemDtoList(order.getItems().stream()
                .map(orderItemConverter::entityToDto).collect(Collectors.toList()));

        return out;
    }
}