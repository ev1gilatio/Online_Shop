package ru.evig.coreservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.evig.coreservice.dto.Cart;
import ru.evig.coreservice.entites.Product;
import ru.evig.coreservice.exceptions.ResourceNotFoundException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductsService productsService;
    private Cart cart;

    @Qualifier("cacheManagerBean")
    private final CacheManager cacheManager;

    @Value("${spring.cache.user.name}")
    private String CACHE_CART;

    @Cacheable(value = "Cart", key = "#cartName")
    public Cart getCurrentCart(String cartName){
        cart = cacheManager.getCache(CACHE_CART).get(cartName, Cart.class);
        if(!Optional.ofNullable(cart).isPresent()){
            cart = new Cart(cartName, cacheManager);
            cacheManager.getCache(CACHE_CART).put(cartName, cart);
        }

        return cart;
    }

    @CachePut(value = "Cart", key = "#cartName")
    public Cart addProductByIdToCart(Long id, String cartName){
        Cart cart = getCurrentCart(cartName);
        if(!cart.addProductCount(id)){
            Product product = productsService.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException("Не удалось найти продукт"));
            cart.addProduct(product);
        }

        return cart;
    }

    @CachePut(value = "Cart", key = "#cartName")
    public Cart removeProduct(Long id, String cartName) {
        Cart cart = getCurrentCart(cartName);
        cart.removeProduct(id);

        return cart;
    }

    @CachePut(value = "Cart", key = "#cartName")
    public Cart decreaseProduct(Long id, String cartName) {
        Cart cart = getCurrentCart(cartName);
        cart.decreaseProduct(id);

        return cart;
    }

    @CachePut(value = "Cart", key = "#cartName")
    public Cart clearCart(String cartName) {
        Cart cart = getCurrentCart(cartName);
        cart.clear();

        return cart;
    }
}