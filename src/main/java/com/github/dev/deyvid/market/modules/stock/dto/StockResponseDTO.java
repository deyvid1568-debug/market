package com.github.dev.deyvid.market.modules.stock.dto;

import java.time.LocalDateTime;

public record StockResponseDTO(
        Long stockId,
        Long productId,
        String productName,
        Long totalQuantity,
        Long minimumStock,
        boolean isLowStock,
        LocalDateTime updatedAt
) {}