package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.request.CreateHeroRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/heroes", produces = APPLICATION_JSON_VALUE)
public class HeroController {

    private final HeroService heroService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<UUID> create(@RequestBody CreateHeroRequest createHeroRequest) {
        UUID heroId = heroService.createHero(createHeroRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(heroId);
    }

    @GetMapping()
    public ResponseEntity<List<CreateHeroRequest>> getAllHeroes(){
        List<CreateHeroRequest> hero = heroService.findAllHeroes();
        return new ResponseEntity<>(hero, HttpStatus.OK);
    }

    @GetMapping(path="/id/{id}")
    public Optional<CreateHeroRequest> getHeroById(@PathVariable UUID id){
        return heroService.getHeroById(id).getBody();
    }

    @GetMapping(path="/name/{name}")
    public ResponseEntity<List<CreateHeroRequest>> getHeroByName(@PathVariable String name){
        List<CreateHeroRequest> hero = heroService.getHeroByName(name);
        return new ResponseEntity<>(hero, HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/hero/{id}")
    public ResponseEntity<Void> deleteHeroById(@PathVariable UUID id){
        heroService.deleteHeroById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @GetMapping(path="/compare/{id1}/{id2}")
//    public ResponseEntity<CreateHeroRequest> compareHeroesById(@PathVariable(name = "id1") UUID id1, @PathVariable(name = "id2") UUID id2){
//        CreateHeroRequest statsComparados = heroService.compareHeroesById(id1, id2);
//        return new ResponseEntity<>(statsComparados, HttpStatus.OK);
//    }
}