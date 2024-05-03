package br.com.fiap.pizzaria.domain.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProdutoRequest(

        @NotNull(message = "O Nome não pode ser nulo")
        String nome,

        @Valid
        @NotNull(message = "O Sabor não pode ser nulo")
        AbsractRequest sabor,


        @Positive(message = "O preço deve ser um número positivo")
        @NotNull(message = "O preço não pode ser nulo")
        BigDecimal preco
) {
}
