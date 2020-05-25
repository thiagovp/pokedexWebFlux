package com.pokedex.reactiveweb.controller;

import com.pokedex.reactiveweb.model.Pokemon;
import com.pokedex.reactiveweb.model.PokemonEvent;
import com.pokedex.reactiveweb.repository.PokemonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/pokemons")
public class PokemonController {

    private PokemonRepository pokemonRepository;

    public PokemonController(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    @GetMapping
    public Flux<Pokemon> getAllPokemons(){return pokemonRepository.findAll();}

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Pokemon>> getPokemon(@PathVariable String id){
        return pokemonRepository.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Pokemon> savePokemon(@RequestBody Pokemon pokemon){
        return pokemonRepository.save(pokemon);
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<Pokemon>> updatePokemon(@PathVariable("id") String id, @RequestBody Pokemon pokemon){
        return pokemonRepository.findById(id).flatMap(existingPokemon ->{
            existingPokemon.setNome(pokemon.getNome());
            existingPokemon.setCategoria(pokemon.getCategoria());
            existingPokemon.setHabilidades(pokemon.getHabilidades());
            existingPokemon.setPeso(pokemon.getPeso());
            return pokemonRepository.save(existingPokemon);
        }).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public Mono<ResponseEntity<Void>> deletePokemon(@PathVariable("id") String id){
        return pokemonRepository.findById(id)
                .flatMap(existingPokemon -> pokemonRepository
                        .delete(existingPokemon)
                        .then(Mono.just(ResponseEntity.ok().<Void>build()))
                ).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public Mono<Void> deleteAllPokemons(){
        return pokemonRepository.deleteAll();
    }

    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<PokemonEvent> getPokemonEvents(){
        System.out.println("teste");
        return Flux.interval(Duration.ofSeconds(5))
                .map(val->new PokemonEvent(val,"Product Event"));
    }
}
