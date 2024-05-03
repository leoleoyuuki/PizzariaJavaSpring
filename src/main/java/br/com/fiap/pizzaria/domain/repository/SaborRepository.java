package br.com.fiap.pizzaria.domain.repository;

import br.com.fiap.pizzaria.domain.entity.Sabor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaborRepository extends JpaRepository<Sabor, Long> {
}
