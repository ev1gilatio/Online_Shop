package ru.evig.coreservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.evig.coreservice.dto.Cart;
import ru.evig.coreservice.services.CartService;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService service;

    @PostMapping
    public Cart getCurrentCart(@RequestBody String cartName) {
        return service.getCurrentCart(cartName);
    }

    @PostMapping("/add/{id}")
    public void addProductToCart(@PathVariable Long id, @RequestBody String cartName) {
        service.addProductByIdToCart(id, cartName);
    }

    @PostMapping("/remove/{id}")
    public void removeProductFromCart(@PathVariable Long id, @RequestBody String cartName) {
        service.removeProduct(id, cartName);
    }

    @PostMapping("/decrease/{id}")
    public void decreaseProductInCart(@PathVariable Long id, @RequestBody String cartName) {
        service.decreaseProduct(id, cartName);
    }

    @PostMapping("/clear")
    public void clearCart(@RequestBody String cartName) {
        service.clearCart(cartName);
    }

}