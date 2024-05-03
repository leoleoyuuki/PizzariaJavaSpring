package br.com.fiap.pizzaria.domain.repository;

import br.com.fiap.pizzaria.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Set<Produto> findBySaborId(Long idSabor);
}
