package br.com.fiap.pizzaria.domain.resource;

import br.com.fiap.pizzaria.domain.dto.request.OpcionalRequest;
import br.com.fiap.pizzaria.domain.dto.request.ProdutoRequest;
import br.com.fiap.pizzaria.domain.dto.response.OpcionalResponse;
import br.com.fiap.pizzaria.domain.dto.response.ProdutoResponse;
import br.com.fiap.pizzaria.domain.entity.Produto;
import br.com.fiap.pizzaria.domain.repository.OpcionalRepository;
import br.com.fiap.pizzaria.domain.repository.SaborRepository;
import br.com.fiap.pizzaria.domain.service.OpcionalService;
import br.com.fiap.pizzaria.domain.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/produtos")
public class ProdutoResource implements ResourceDTO<ProdutoRequest, ProdutoResponse> {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private SaborRepository saborRepository;

    @Autowired
    private OpcionalRepository opcionalRepository;

    @Autowired
    private OpcionalService opcionalService;

    @GetMapping
    public ResponseEntity<Collection<ProdutoResponse>> findAll(
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "preco", required = false) BigDecimal preco,
            @RequestParam(name = "sabor", required = false) Long idSabor,
            @RequestParam(name = "opcional", required = false) Long idOpcional
    ) {

        var opcional = Objects.nonNull(idOpcional) ? opcionalRepository
                .findById(idOpcional)
                .orElse(null): null;


        var sabor = Objects.nonNull(idSabor) ? saborRepository
                .findById(idSabor)
                .orElse(null): null;

        var produto = Produto.builder()
                .nome(nome)
                .sabor(sabor)
                .preco(preco)
                .opcionais(opcional != null ? Set.of(opcional) : null)
                .build();

        ExampleMatcher matcher = ExampleMatcher
                .matchingAll()
                .withIgnoreNullValues()
                .withIgnoreCase();

        Example<Produto> example = Example.of(produto, matcher);
        var encontrados = produtoService.findAll(example);

        var response = encontrados.stream().map(produtoService::toResponse).toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> findById(@PathVariable Long id) {
        var entity = produtoService.findById(id);
        if (Objects.isNull(entity)) return ResponseEntity.notFound().build();
        var response = produtoService.toResponse(entity);
        return ResponseEntity.ok(response);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<ProdutoResponse> save(@RequestBody @Valid ProdutoRequest produtoRequest) {
        var entity = produtoService.toEntity(produtoRequest);
        var saved = produtoService.save(entity);
        var response = produtoService.toResponse(saved);

        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @Transactional
    @PostMapping("/{id}/opcionais")
    public ResponseEntity<ProdutoResponse> addOptionalToProduct(@PathVariable Long id, @RequestBody @Valid OpcionalRequest opcionalRequest) {
        var produto = produtoService.findById(id);
        if (Objects.isNull(produto)) return ResponseEntity.notFound().build();

        var opcional = opcionalService.toEntity(opcionalRequest);
        var savedOpcional = opcionalService.save(opcional);

        produto.getOpcionais().add(savedOpcional);
        var updatedProduto = produtoService.save(produto);

        var response = produtoService.toResponse(updatedProduto);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}/opcionais")
    public ResponseEntity<Collection<OpcionalResponse>> getOptionals(@PathVariable Long id) {
        var produto = produtoService.findById(id);
        if (Objects.isNull(produto)) return ResponseEntity.notFound().build();

        var opcionais = produto.getOpcionais();
        var response = opcionais.stream().map(opcionalService::toResponse).toList();
        return ResponseEntity.ok(response);
    }

}