package br.com.fiap.pizzaria.domain.repository;

import br.com.fiap.pizzaria.domain.entity.Opcional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface OpcionalRepository extends JpaRepository<Opcional, Long> {

    @Query("SELECT p.opcionais FROM Produto p where p.id = :idProduto")
    Set<Opcional> findByProdutoId(Long idProduto);
}
