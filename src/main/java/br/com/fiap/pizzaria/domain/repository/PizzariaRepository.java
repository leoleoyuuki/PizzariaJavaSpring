package br.com.fiap.pizzaria.domain.repository;

import br.com.fiap.pizzaria.domain.entity.Pizzaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzariaRepository extends JpaRepository<Pizzaria, Long> {
}
