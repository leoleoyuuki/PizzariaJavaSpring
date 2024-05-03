package br.com.fiap.pizzaria.domain.resource;

import br.com.fiap.pizzaria.domain.dto.request.OpcionalRequest;
import br.com.fiap.pizzaria.domain.dto.response.OpcionalResponse;
import br.com.fiap.pizzaria.domain.entity.Opcional;
import br.com.fiap.pizzaria.domain.entity.Produto;
import br.com.fiap.pizzaria.domain.repository.SaborRepository;
import br.com.fiap.pizzaria.domain.service.OpcionalService;
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
import java.util.Objects;

@RestController
@RequestMapping("/opcionais")
public class OpcionalResource implements ResourceDTO<OpcionalRequest, OpcionalResponse> {

    @Autowired
    private OpcionalService opcionalService;

    @Autowired
    private SaborRepository saborRepository;

    @GetMapping
    public ResponseEntity<Collection<OpcionalResponse>> findAll(
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "sabor", required = false) Long idSabor,
            @RequestParam(name = "preco", required = false) BigDecimal preco
    ) {

        var sabor = Objects.nonNull(idSabor) ? saborRepository
                .findById(idSabor)
                .orElseThrow() : null;

        var opcional = Opcional.builder()
                .nome(nome)
                .sabor(sabor)
                .preco(preco)
                .build();

        ExampleMatcher matcher = ExampleMatcher
                .matchingAll()
                .withIgnoreNullValues()
                .withIgnoreCase();

        Example<Opcional> example = Example.of(opcional, matcher);
        Collection<Opcional> opcionais = opcionalService.findAll(example);

        var response = opcionais.stream().map(opcionalService::toResponse).toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OpcionalResponse> findById(@PathVariable Long id) {
        var entity = opcionalService.findById(id);
        if (Objects.isNull(entity)) return ResponseEntity.notFound().build();
        var response = opcionalService.toResponse(entity);
        return ResponseEntity.ok(response);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<OpcionalResponse> save(@RequestBody @Valid OpcionalRequest opcionalRequest) {
        var entity = opcionalService.toEntity(opcionalRequest);
        var saved = opcionalService.save(entity);
        var response = opcionalService.toResponse(saved);

        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }
}