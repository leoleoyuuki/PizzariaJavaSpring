package br.com.fiap.pizzaria.domain.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record OpcionalRequest(
        String nome,

        @Valid
        AbsractRequest sabor,

        @Positive(message = "O preço deve ser um número positivo")
        BigDecimal preco
) {
}
