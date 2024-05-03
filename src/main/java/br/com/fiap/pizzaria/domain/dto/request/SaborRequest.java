package br.com.fiap.pizzaria.domain.dto.request;

import jakarta.validation.constraints.NotNull;

public record SaborRequest(

        @NotNull(message = "A descrição não pode ser nula")
        String descricao,

        @NotNull(message = "O nome não pode ser nulo")
        String nome
) {
}
