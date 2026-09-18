package com.github.dev.deyvid.market.modules.product.dto;

import com.github.dev.deyvid.market.modules.product.model.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponseDTO(
        Long id,
        String name,
        String description,
        String sku,
        BigDecimal price,
        Boolean active,
        LocalDateTime createdAt
) {
    public ProductResponseDTO(Product product) {
        this(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getSku(),
                product.getPrice(),
                product.getActive(),
                product.getCreatedAt()
        );
    }
}