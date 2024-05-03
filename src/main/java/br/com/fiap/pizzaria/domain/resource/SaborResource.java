package br.com.fiap.pizzaria.domain.resource;

import br.com.fiap.pizzaria.domain.dto.request.SaborRequest;
import br.com.fiap.pizzaria.domain.dto.response.SaborResponse;
import br.com.fiap.pizzaria.domain.entity.Sabor;
import br.com.fiap.pizzaria.domain.service.SaborService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/sabores")
public class SaborResource implements ResourceDTO<SaborRequest, SaborResponse> {

    @Autowired
    private SaborService saborService;

    @GetMapping
    public ResponseEntity<Collection<SaborResponse>> findAll(
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "descricao", required = false) String descricao
    ) {
        var sabor = Sabor.builder()
                .nome(nome)
                .descricao(descricao)
                .build();

        ExampleMatcher matcher = ExampleMatcher
                .matchingAll()
                .withIgnoreNullValues()
                .withIgnoreCase();

        Example<Sabor> example = Example.of(sabor, matcher);
        Collection<Sabor> sabores = saborService.findAll(example);

        var response = sabores.stream().map(saborService::toResponse).toList();
        return ResponseEntity.ok(response);
    }



        @GetMapping("/{id}")
        public ResponseEntity<SaborResponse> findById(@PathVariable Long id) {
            var entity = saborService.findById(id);
            if (Objects.isNull(entity)) return ResponseEntity.notFound().build();
            var response = saborService.toResponse(entity);
            return ResponseEntity.ok(response);
        }

    @Transactional
    @PostMapping
    public ResponseEntity<SaborResponse> save(@RequestBody @Valid SaborRequest saborRequest) {
        var entity = saborService.toEntity(saborRequest);
        var saved = saborService.save(entity);
        var response = saborService.toResponse(saved);

        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }
}