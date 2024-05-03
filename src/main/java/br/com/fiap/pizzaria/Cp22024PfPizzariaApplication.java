package br.com.fiap.pizzaria;

import br.com.fiap.pizzaria.domain.entity.Opcional;
import br.com.fiap.pizzaria.domain.entity.Pizzaria;
import br.com.fiap.pizzaria.domain.entity.Produto;
import br.com.fiap.pizzaria.domain.entity.Sabor;
import br.com.fiap.pizzaria.domain.repository.PizzariaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.stream.Collectors;

@SpringBootApplication
public class Cp22024PfPizzariaApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger( Cp22024PfPizzariaApplication.class );

    @Autowired
    private PizzariaRepository pizzariaRepository;


    public static void main(String[] args) {
        SpringApplication.run( Cp22024PfPizzariaApplication.class, args );
    }


    @Transactional
    @Override
    public void run(String... args) throws Exception {

        long pizzarias = pizzariaRepository.count();

        log.info( "Existem {} pizzarias Cadastradas", pizzarias );

        if (pizzariaRepository.count() < 1) {
            log.info( "Criando a primeira pizzaria da Rede" );
            criaDonBenezinho();
        }
    }

    private void criaDonBenezinho() {
        var mucarela = Sabor.builder()
                .nome( "Muçarela" )
                .descricao( "Delicionsa muçarela" )
                .build();

        var calabresa = Sabor.builder()
                .nome( "Calabresa" )
                .descricao( "Saborosa calabresa" )
                .build();

        var calabresaComMucarela = Sabor.builder()
                .nome( "Calabresa com Muçarela" )
                .descricao( "A combinação perfeita da nossa saborosa calabresa com o queijo muçarela de arrancar suspiros" )
                .build();

        var cola = Sabor.builder()
                .nome( "Cola" )
                .build();

        var laranja = Sabor.builder()
                .nome( "Laranja" )
                .build();

        var guarana = Sabor.builder()
                .nome( "Guarana" )
                .build();

        var uva = Sabor.builder()
                .nome( "Uva" )
                .build();
        var limao = Sabor.builder()
                .nome( "Limão" )
                .build();

        var catupiri = Sabor.builder()
                .nome( "Catupiri" )
                .build();

        var cocaCola = Opcional.builder()
                .nome( "Coca-Cola" )
                .sabor( cola )
                .preco( BigDecimal.valueOf( 19.99 ) )
                .build();

        var sprite = Opcional.builder()
                .nome( "Sprite" )
                .sabor( limao )
                .preco( BigDecimal.valueOf( 12.99 ) )
                .build();

        var fantaLaranja = Opcional.builder()
                .nome( "Fanta" )
                .sabor( laranja )
                .preco( BigDecimal.valueOf( 12.99 ) )
                .build();

        var fantaUva = Opcional.builder()
                .nome( "Fanta" )
                .sabor( uva )
                .preco( BigDecimal.valueOf( 12.99 ) )
                .build();

        var bordaDeCatupiri = Opcional.builder()
                .nome( "Borda de Catupiri" )
                .sabor( catupiri )
                .preco( BigDecimal.valueOf( 9.99 ) )
                .build();

        var opcionais = Arrays.asList( cocaCola, fantaUva, fantaLaranja, sprite, bordaDeCatupiri );

        var pizzaMucarela = Produto.builder()
                .nome( "Pizza" )
                .sabor( mucarela )
                .preco( BigDecimal.valueOf( 59.99 ) )
                .opcionais( opcionais.stream().collect( Collectors.toSet() ) )
                .build();

        var pizzaCalabresa = Produto.builder()
                .nome( "Pizza" )
                .sabor( calabresa )
                .preco( BigDecimal.valueOf( 59.99 ) )
                .opcionais( opcionais.stream().collect( Collectors.toSet() ) )
                .build();

        var pizzaCalabresaComMucarela = Produto.builder()
                .nome( "Pizza" )
                .sabor( calabresaComMucarela )
                .preco( BigDecimal.valueOf( 59.99 ) )
                .opcionais( opcionais.stream().collect( Collectors.toSet() ) )
                .build();

        var cardapio = Arrays.asList( pizzaMucarela, pizzaCalabresa, pizzaCalabresaComMucarela );

        var pizzariaDonBenezinho = Pizzaria.builder()
                .nome( "Don Benezinho" )
                .cardapio( cardapio.stream().collect( Collectors.toSet() ) )
                .build();

        pizzariaRepository.save( pizzariaDonBenezinho );
    }

}
