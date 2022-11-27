package ru.evig.coreservice.converters;

import org.springframework.stereotype.Component;
import ru.evig.coreservice.dto.ProductDto;
import ru.evig.coreservice.entites.Product;

@Component
public class ProductConverter {
    public Product dtoToEntity(ProductDto productDto) {
        return new Product(productDto.getId(), productDto.getTitle(), productDto.getPrice());
    }

    public ProductDto entityToDto(Product product) {
        return new ProductDto(product.getId(), product.getTitle(), product.getPrice());
    }
}