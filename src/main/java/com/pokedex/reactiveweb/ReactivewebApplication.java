package com.pokedex.reactiveweb;

import com.pokedex.reactiveweb.model.Pokemon;
import com.pokedex.reactiveweb.repository.PokemonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class ReactivewebApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactivewebApplication.class, args);
	}

	@Bean
	CommandLineRunner init (ReactiveMongoOperations operations, PokemonRepository repository){
		return args -> {
			Flux<Pokemon> pokemonFlux =  Flux.just(
					new Pokemon(null, "Bulbassauro","Semente","Solar Beam",6.09),
					new Pokemon(null, "Charmander","Lagarto","Ember",8.09),
					new Pokemon(null, "Squirtle","Tartaruga","Hidro Pump",10.0),
					new Pokemon(null, "Eevee","Raposa","Fast Attack",5.15)
			).flatMap(repository::save);
			pokemonFlux.thenMany(repository.findAll()).subscribe(System.out::println);
			pokemonFlux.skip(1);
		};
	}

}
