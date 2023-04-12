package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.model.dto.ComparedHeroDto;
import br.com.gubee.interview.model.dto.HeroDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
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
    private final HeroRepository heroRepository;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<UUID> create(@RequestBody HeroDto heroDto) {
        UUID heroId = heroService.createHero(heroDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(heroId);
    }

    @GetMapping()
    public ResponseEntity<List<HeroDto>> getAllHeroes(){
        List<HeroDto> hero = heroService.findAllHeroes();
        return new ResponseEntity<>(hero, HttpStatus.OK);
    }

    @GetMapping(path="/id/{id}")
    public ResponseEntity<Optional<HeroDto>> getHeroById(@PathVariable UUID id){
        return heroService.getHeroById(id);
    }

    @GetMapping(path="/name/{name}")
    public ResponseEntity<List<HeroDto>> getHeroByName(@PathVariable String name){
        List<HeroDto> hero = heroService.getHeroByName(name);
        return new ResponseEntity<>(hero, HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/hero/{id}")
    public ResponseEntity<Void> deleteHeroById(@PathVariable UUID id){
        return heroService.deleteHeroById(id);
    }

    @PutMapping("/{heroId}")
    public ResponseEntity<Void> updateHero(@PathVariable UUID heroId, @RequestBody HeroDto heroDto) throws ChangeSetPersister.NotFoundException {

        return heroService.updateHero(heroId, heroDto);
    }

    @GetMapping("/{hero1}/{hero2}")
    public ResponseEntity<ComparedHeroDto> compareHeroes(@PathVariable UUID hero1, @PathVariable UUID hero2 ) throws ChangeSetPersister.NotFoundException {
        ComparedHeroDto comparedHeroDto = heroService.compareHeroes(hero1,hero2);
        return new ResponseEntity<>(comparedHeroDto,HttpStatus.OK);
    }

}