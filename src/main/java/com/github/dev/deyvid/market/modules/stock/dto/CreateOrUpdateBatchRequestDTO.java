package com.github.dev.deyvid.market.modules.stock.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateOrUpdateBatchRequestDTO(
        @NotBlank(message = "O número do lote é obrigatório")
        String batchNumber,

        @NotNull(message = "O ID do produto é obrigatório")
        Long productId,

        @NotNull(message = "A quantidade é obrigatória")
        @Positive(message = "A quantidade deve ser maior que zero")
        BigDecimal quantity,

        @NotNull(message = "O preço de compra é obrigatório")
        @DecimalMin(value = "0.00", message = "O preço de compra deve ser maior que zero")
        BigDecimal buyingPrice,

        LocalDate expireDate
) {}