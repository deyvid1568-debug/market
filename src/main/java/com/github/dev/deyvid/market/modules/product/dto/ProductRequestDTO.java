package com.github.dev.deyvid.market.modules.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductRequestDTO(

        @NotBlank(message = "O nome do produto é obrigatório")
        @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
        String name,

        String description,

        @NotBlank(message = "O SKU é obrigatório")
        @Size(min = 3, max = 30, message = "O SKU deve ter entre 3 e 30 caracteres")
        String sku,

        @NotNull(message = "O preço é obrigatório")
        @Positive(message = "O preço deve ser um valor positivo")
        BigDecimal price,

        Boolean active
) {}