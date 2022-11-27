package ru.evig.coreservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.evig.coreservice.converters.OrderConverter;
import ru.evig.coreservice.dto.OrderDetailsDto;
import ru.evig.coreservice.dto.OrderDto;
import ru.evig.coreservice.services.OrderService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderConverter orderConverter;

    @PostMapping("/{cartName}")
    public void createOrder(@RequestHeader String username,
                            @RequestBody OrderDetailsDto orderDetailsDto,
                            @PathVariable String cartName) {
        orderService.createOrder(username, orderDetailsDto, cartName);
    }

    @GetMapping
    public List<OrderDto> getCurrentOrders(@RequestHeader String username) {
        return orderService.findOrdersByUserName(username).stream()
                .map(orderConverter::entityToDto).collect(Collectors.toList());
    }
}