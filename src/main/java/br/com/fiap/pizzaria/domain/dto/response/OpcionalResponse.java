package br.com.fiap.pizzaria.domain.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OpcionalResponse(
        String nome,
        Long id,
        BigDecimal preco,
        SaborResponse sabor
) {
}
