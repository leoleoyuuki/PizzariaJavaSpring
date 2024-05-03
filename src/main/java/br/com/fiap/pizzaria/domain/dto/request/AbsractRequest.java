package br.com.fiap.pizzaria.domain.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AbsractRequest(
        @Positive(message = "O id deve ser um número positivo")
        @NotNull(message = "O id não pode ser nulo")
        Long id
) {
}
