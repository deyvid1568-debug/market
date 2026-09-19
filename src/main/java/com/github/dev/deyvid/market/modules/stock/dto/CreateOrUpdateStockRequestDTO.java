package com.github.dev.deyvid.market.modules.stock.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CreateOrUpdateStockRequestDTO(
        @NotNull(message = "O ID do produto é obrigatório")
        Long productId,

        @NotNull(message = "O estoque mínimo é obrigatório")
        @PositiveOrZero(message = "O estoque mínimo não pode ser negativo")
        BigDecimal minimumStock
) {}